package tictactoe.app.state;

import genericapp.AppStateEvent;
import lombok.Getter;
import tictactoe.backend.manager.GameManager;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.connector.event.backend.listener.GameStateChangeListener;
import tictactoe.connector.event.ui.base.IGamingStateUI;

@Getter
public class GamingState<UIType extends IGamingStateUI> extends AbstractTicTacToeAppState<UIType> implements GameStateChangeListener {

    public static final AppStateEvent PAUSE = () -> false;
    public static final AppStateEvent GAME_WON = () -> true;
    public static final AppStateEvent GAME_LOST = () -> true;
    public static final AppStateEvent GAME_DRAWN = () -> true;

    private GameManager<UIType> gameManager;

    public GamingState(Integer id, LocalGameManager<UIType> gameManager) {
        super(id, gameManager.getUI());
        this.gameManager = gameManager;
        this.gameManager.getGameEngine().setGameStateChangeListener(this);
    }

    @Override
    public void playerWon(int playerNumber) {
        this.getAppStateEventHandler().handleAppStateEvent(this, GAME_WON);
    }

    @Override
    public void playerLost(int playerNumber) {
        this.getAppStateEventHandler().handleAppStateEvent(this, GAME_LOST);
    }

    @Override
    public void draw() {
        this.getAppStateEventHandler().handleAppStateEvent(this, GAME_DRAWN);
    }
}
