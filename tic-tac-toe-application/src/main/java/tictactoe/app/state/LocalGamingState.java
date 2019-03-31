package tictactoe.app.state;

import generic.app.AppStateEvent;
import lombok.Getter;
import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.ui.base.IGamingStateUI;

@Getter
public class LocalGamingState<UIType extends IGamingStateUI> extends GamingState<UIType> {

    public static final AppStateEvent PAUSE = () -> false;
    public static final AppStateEvent GAME_WON = () -> true;
    public static final AppStateEvent GAME_LOST = () -> true;
    public static final AppStateEvent GAME_DRAWN = () -> true;

    private final GameEngine gameEngine;

    public LocalGamingState(Integer id, GameEngine gameEngine, UIType ui) {
        super(id, ui);
        this.gameEngine = gameEngine;
        getGameEngine().setGameStateChangeListener(this);
        getGameEngine().getBoard().setTileEventListener(this);
        getUi().setListener(this);
    }

    @Override
    public void playerWon(int playerNumber) {
        getAppStateEventHandler().handleAppStateEvent(this, GAME_WON);
    }

    @Override
    public void playerLost(int playerNumber) {
        getAppStateEventHandler().handleAppStateEvent(this, GAME_LOST);
    }

    @Override
    public void draw() {
        getAppStateEventHandler().handleAppStateEvent(this, GAME_DRAWN);
    }

    @Override
    public void tileClicked(int row, int col) {
        getGameEngine().acceptNextPlayerMark(row, col);
    }

    @Override
    public void pauseGame() {
        getAppStateEventHandler().handleAppStateEvent(this, PAUSE);
    }

    ///////////////////////////////////////////////////////////////////////////
    // TileEventListener interface methods
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void valueChanged(int row, int col, int oldValue, int newValue) {
        assert oldValue == -1 : String.format("Player shouldn't change value of cell (%d,%d) to %d, because it had value %d", row, col, newValue, oldValue);
        getUi().mark(getGameEngine().getCurrentPlayerNumber(), row, col);
    }

    @Override
    public void valueErased(int row, int col, int oldValue) {
        ui.removeMark(row, col);
    }

    ///////////////////////////////////////////////////////////////////////////
    // public methods
    ///////////////////////////////////////////////////////////////////////////

    public String getWinnerName() {
        return String.valueOf(getGameEngine().getCurrentPlayerNumber());
    }

    public String getPlayerName() {
        return String.valueOf(getGameEngine().getCurrentPlayerNumber());
    }
}
