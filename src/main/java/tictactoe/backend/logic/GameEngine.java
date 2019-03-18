package tictactoe.backend.logic;

import tictactoe.backend.Utils;
import tictactoe.backend.helper.Direction;
import tictactoe.backend.listener.GameStateChangeEventListener;
import tictactoe.backend.logic.exception.IllegalEngineStateException;
import tictactoe.backend.model.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * A basic tic-tac-toe engine designed for only 2 players.
 */
@Getter @Setter
public class GameEngine {
    @NonNull
    private final GameConfig gameConfig;
    @NonNull
    private final Board board;

    @Setter(AccessLevel.NONE)
    private int currentPlayerNumber;

    @NonNull
    private GameStateChangeEventListener gameStateChangeEventListener;

    @Setter(AccessLevel.NONE)
    private Integer winnerNumber;

    public GameEngine(GameConfig gameConfig, Board board) {
        this.gameConfig = gameConfig;
        this.board = board;
        this.currentPlayerNumber = Utils.randomInt(gameConfig.getMaxPlayersCount());
    }

    public void acceptNextPlayerMark(int row, int col) {
        if(winnerNumber != null)
            throw new IllegalEngineStateException(winnerNumber);
        Board.Tile tile = board.getTile(row, col);
        if(tile.isEmpty()) {
            tile.setValue(currentPlayerNumber);
            if(isCurrentPlayerWon(row, col))
                gameStateChangeEventListener.playerWon(winnerNumber = currentPlayerNumber);
            else
                currentPlayerNumber = (currentPlayerNumber + 1) % gameConfig.getMaxPlayersCount();
        }
        else {
            System.out.println(String.format("Player %d marked tile (%d,%d), which already was marked as %d", currentPlayerNumber, row, col, tile.getValue()));
        }
    }

    /**
     * Checks weather current player won the game or not.
     * Function always should be called right after current player marked any tile.
     * @param lastMarkRow lastMarkRow number of marked tile
     * @param lastMarkCol column number of marked tile
     */
    private boolean isCurrentPlayerWon(int lastMarkRow, int lastMarkCol) {
        assert board.getTile(lastMarkRow, lastMarkCol).getValue() == currentPlayerNumber : "tile value at (" + lastMarkRow + "," + lastMarkCol + ") should be equal to " + currentPlayerNumber;
        assert gameStateChangeEventListener != null : "gameStateChangeEventListener should not be null";
        return isCurrentPlayerWonInDirection(lastMarkRow, lastMarkCol, Direction.LEFT, Direction.RIGHT) ||
                isCurrentPlayerWonInDirection(lastMarkRow, lastMarkCol, Direction.UP, Direction.DOWN) ||
                isCurrentPlayerWonInDirection(lastMarkRow, lastMarkCol, Direction.LEFT_UP, Direction.RIGHT_DOWN) ||
                isCurrentPlayerWonInDirection(lastMarkRow, lastMarkCol, Direction.RIGHT_UP, Direction.LEFT_DOWN);
    }

    private boolean isCurrentPlayerWonInDirection(int row, int col, Direction left, Direction right) {
        int count = 0;
        count += playerTilesCount(row, col, left);
        count += playerTilesCount(row, col, right);
        // NOTE: here assumed that tile at (row,col) already marked by current player
        // so we should add 1 for that tile
        return isGameWon(count + 1);
    }

    /**
     * Starting tile is excluded.
     */
    private int playerTilesCount(int startRow, int startCol, Direction countDirection) {
        int count = 0;
        startRow += countDirection.getDx();
        startCol += countDirection.getDy();
        while(board.isInsideBoard(startRow, startCol) && board.getTile(startRow, startCol).getValue() == currentPlayerNumber) {
            count++;
            startRow += countDirection.getDx();
            startCol += countDirection.getDy();
        }
        return count;
    }

    /**
     * Checks weather game has been won by current player or not.
     *
     * @param consecutiveMarkCount count of consecutive marks by current player in any direction.
     * @return a boolean, determining weather game has been won by current player or not.
     */
    private boolean isGameWon(int consecutiveMarkCount) {
        return consecutiveMarkCount >= gameConfig.getWinLength();
    }
}
