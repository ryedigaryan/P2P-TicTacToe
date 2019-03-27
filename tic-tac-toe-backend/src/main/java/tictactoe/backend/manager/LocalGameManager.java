package tictactoe.backend.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.ui.base.IGamingStateUI;

/**
 * A basic game manager which is designed for a game in one computer.
 */
@Getter @Setter
public class LocalGameManager<UIType extends IGamingStateUI> implements GameManager<UIType> {

    private final GameEngine gameEngine;
    private final UIType ui;

    public LocalGameManager(GameEngine gameEngine, UIType ui) {
        this.gameEngine = gameEngine;
        this.ui = ui;
        getGameEngine().getBoard().setTileEventListener(this);
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

    @Override
    public void processPlayerInput(int row, int col) {
        gameEngine.acceptNextPlayerMark(row, col);
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
}
