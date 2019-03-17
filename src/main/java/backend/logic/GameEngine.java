package backend.logic;

import backend.Utils;
import backend.helper.Direction;
import backend.helper.GameConfig;
import backend.listener.GameStateChangeEventListener;
import backend.model.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * A basic tic-tac-toe engine designed for only 2 players.
 */
@Getter @Setter
public class GameEngine {
    private final GameConfig gameConfig;
    private final Board board;

    @Setter(AccessLevel.NONE)
    private int currentPlayerNumber;

    private GameStateChangeEventListener gameStateChangeEventListener;

    public GameEngine(GameConfig gameConfig, Board board) {
        this.gameConfig = gameConfig;
        this.board = board;
        this.currentPlayerNumber = Utils.randomInt(gameConfig.getMaxPlayersCount());
    }

    public void acceptPlayerMark(int row, int col) {
        Board.Tile tile = board.getTile(row, col);
        if(tile.isEmpty()) {
            tile.setValue(currentPlayerNumber);
        }
        checkIfPlayerWon(row, col);
    }

    /**
     * Checks weather current player won the game or not.
     * Function always should be called right after current player marked any tile.
     * @param lastMarkRow lastMarkRow number of marked tile
     * @param lastMarkCol column number of marked tile
     */
    private void checkIfPlayerWon(int lastMarkRow, int lastMarkCol) {
        assert board.getTile(lastMarkRow, lastMarkCol).getValue() == currentPlayerNumber : "tile value at (" + lastMarkRow + "," + lastMarkCol + ") should be equal to " + currentPlayerNumber;
        assert gameStateChangeEventListener != null : "gameStateChangeEventListener should not be null";
        if (playerHasWonInDirections(lastMarkRow, lastMarkCol, Direction.LEFT, Direction.RIGHT))
            return;
        if (playerHasWonInDirections(lastMarkRow, lastMarkCol, Direction.UP, Direction.DOWN))
            return;
        if (playerHasWonInDirections(lastMarkRow, lastMarkCol, Direction.LEFT_UP, Direction.RIGHT_DOWN))
            return;
        if (playerHasWonInDirections(lastMarkRow, lastMarkCol, Direction.RIGHT_UP, Direction.LEFT_DOWN))
            return;
    }

    private boolean playerHasWonInDirections(int row, int col, Direction left, Direction right) {
        int count = 0;
        count += playerTilesCount(row, col, left);
        count += playerTilesCount(row, col, right);
        return checkGameState(count + 1);
    }

    private int __playerTilesCount(int row, int col) {
        int count = Arrays.stream(Direction.values())
                .mapToInt(d -> playerTilesCount(row, col, d))
                .sum();
        // NOTE: here assumed that at tile at (row,col) already player's number is placed
        return count + 1;
    }

    /**
     * Starting tile is excluded.
     */
    private int playerTilesCount(int startRow, int startCol, Direction countDirection) {
        int count = 0;
        startRow += countDirection.getDx();
        startCol += countDirection.getDy();
        while(board.getTile(startRow, startCol).getValue() == currentPlayerNumber) {
            count++;
        }
        return count;
    }

    /**
     * Checks weather game has been won by current player or not.
     *
     * @param consecutiveMarkCount count of consecutive marks by current player in any direction.
     * @return a boolean, determining weather game has been won by current player or not.
     */
    private boolean checkGameState(int consecutiveMarkCount) {
        if(consecutiveMarkCount == gameConfig.getWinLength()) {
            gameStateChangeEventListener.playerWon(currentPlayerNumber);
            return true;
        }
        return false;
    }
}
