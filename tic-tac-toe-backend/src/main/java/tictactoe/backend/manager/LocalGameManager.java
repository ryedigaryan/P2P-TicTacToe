package tictactoe.backend.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.event.backend.listener.TileEventListener;
import tictactoe.connector.event.ui.base.IGamingStateUI;
import tictactoe.connector.event.ui.listener.GamingStateUIListener;

/**
 * A basic game manager which is designed for a game in one computer.
 */
@Getter @Setter
public class LocalGameManager<UIType extends IGamingStateUI> implements GameManager<UIType> {

    private final GameEngine gameEngine;
    @Getter(AccessLevel.NONE)
    private final UIType ui;

    public LocalGameManager(GameEngine gameEngine, UIType ui) {
        this.gameEngine = gameEngine;
        this.ui = ui;
        getGameEngine().getBoard().setTileEventListener(this);
        getUI().setListener(this);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GameManager interface methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public String getWinnerName() {
        return String.valueOf(getGameEngine().getCurrentPlayerNumber());
    }

    @Override
    public String getPlayerName() {
        return String.valueOf(getGameEngine().getCurrentPlayerNumber());
    }

    @Override
    public UIType getUI() {
        return ui;
    }

    ///////////////////////////////////////////////////////////////////////////
    // TileEventListener interface methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void valueChanged(int row, int col, int oldValue, int newValue) {
        assert oldValue == -1 : String.format("Player shouldn't change value of cell (%d,%d) to %d, because it had value %d", row, col, newValue, oldValue);
        ui.mark(gameEngine.getCurrentPlayerNumber(), row, col);
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        ui.removeMark(row, col);
    }

    ///////////////////////////////////////////////////////////////////////////
    // GamingStateUIListener interface methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void tileClicked(int row, int col) {
        gameEngine.acceptNextPlayerMark(row, col);
    }

    @Override
    public void pauseGame() {
        // TODO: 3/25/2019
    }
}
