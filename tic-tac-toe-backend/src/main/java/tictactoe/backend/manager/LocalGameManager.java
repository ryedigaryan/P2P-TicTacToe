package tictactoe.backend.manager;

import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.event.backend.listener.GameStateChangeListener;
import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.event.backend.listener.TileEventListener;
import tictactoe.backend.grdon.TemporarySolution;
import tictactoe.connector.event.ui.base.IGameBoardUI;
import tictactoe.connector.event.ui.listener.TileClickListener;

import java.util.stream.IntStream;

/**
 * A basic game manager which is designed for a game in one computer.
 */
@Getter @Setter
public class LocalGameManager implements TileEventListener, GameStateChangeListener, TileClickListener {

    private final GameEngine gameEngine;
    private final IGameBoardUI boardUI;

    // TODO: 3/18/2019 Extend GameBoardUI from interface, which will be common for all UserInterfaces which wanna play this game...
    public LocalGameManager(GameEngine gameEngine, IGameBoardUI boardUI) {
        this.gameEngine = gameEngine;
        this.boardUI = boardUI;
    }

    public String[] getPlayerNames() {
        return IntStream.range(0, getGameEngine().getGameConfig().getMaxPlayersCount())
                .mapToObj(Integer::toString)
                .toArray(String[]::new);
    }

    @Override
    public void valueChanged(int row, int col, int oldValue, int newValue) {
        assert oldValue == -1 : String.format("Player shouldn't change value of cell (%d,%d) to %d, because it had value %d", row, col, newValue, oldValue);
        if(gameEngine.getCurrentPlayerNumber() == 0) {
            boardUI.markX(row, col);
        }
        else if(gameEngine.getCurrentPlayerNumber() == 1) {
            boardUI.markO(row, col);
        }
        else {
            assert false : "GameEngine.currentPlayerNumber should be 0 or 1";
        }
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        boardUI.removeMark(row, col);
    }

    @Override
    public void playerWon(int playerNumber) {
        TemporarySolution.showWinnerNumber(playerNumber);
    }

    @Override
    public void tileClicked(int row, int col) {
        gameEngine.acceptNextPlayerMark(row, col);
    }
}
