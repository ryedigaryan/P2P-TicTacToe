package tictactoe.app.state;

import genericapp.AppStateEvent;
import lombok.Getter;
import tictactoe.backend.manager.GameManager;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.connector.backend.listener.GameStateChangeListener;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

@Getter
public class GamingState<UIType extends IGamingStateUI> extends AbstractTicTacToeAppState<UIType> implements GameStateChangeListener, GamingStateUIListener {

    public static final AppStateEvent PAUSE = () -> false;
    public static final AppStateEvent GAME_WON = () -> true;
    public static final AppStateEvent GAME_LOST = () -> true;
    public static final AppStateEvent GAME_DRAWN = () -> true;

    private GameManager<UIType> gameManager;

    public GamingState(Integer id, LocalGameManager<UIType> gameManager) {
        super(id, gameManager.getUI());
        this.gameManager = gameManager;
        getGameManager().getGameEngine().setGameStateChangeListener(this);
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
        gameManager.processPlayerInput(row, col);
    }

    @Override
    public void pauseGame() {
        getAppStateEventHandler().handleAppStateEvent(this, PAUSE);
    }
}
