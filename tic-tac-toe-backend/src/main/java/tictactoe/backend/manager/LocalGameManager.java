package tictactoe.backend.manager;

import lombok.Getter;
import lombok.Setter;
import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.event.backend.listener.TileEventListener;
import tictactoe.connector.event.ui.base.IGamingStateUI;
import tictactoe.connector.event.ui.listener.GamingStateUIListener;

import java.util.stream.IntStream;

/**
 * A basic game manager which is designed for a game in one computer.
 */
@Getter @Setter
public class LocalGameManager implements TileEventListener, GamingStateUIListener {

    private final GameEngine gameEngine;
    private final IGamingStateUI boardUI;

    public LocalGameManager(GameEngine gameEngine, IGamingStateUI boardUI) {
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
        boardUI.mark(gameEngine.getCurrentPlayerNumber(), row, col);
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        boardUI.removeMark(row, col);
    }

    @Override
    public void tileClicked(int row, int col) {
        gameEngine.acceptNextPlayerMark(row, col);
    }

    @Override
    public void pauseGame() {
        // TODO: 3/25/2019
    }
}
