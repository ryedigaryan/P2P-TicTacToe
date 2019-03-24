package tictactoe.app.flow.item;

import genericapp.AppStateEvent;
import lombok.Getter;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.connector.event.backend.listener.GameStateChangeListener;
import tictactoe.connector.event.ui.base.IGameBoardUI;

@Getter
public class GamingScene extends AbstractTicTacToeAppState<IGameBoardUI> implements GameStateChangeListener {

    public static final AppStateEvent PAUSE = () -> false;
    public static final AppStateEvent GAME_WON = () -> true;
    public static final AppStateEvent GAME_LOST = () -> true;
    public static final AppStateEvent GAME_DRAWN = () -> true;

    private LocalGameManager localTicTacToeGameManager;

    public GamingScene(Integer id, LocalGameManager gameManager) {
        super(id, gameManager.getBoardUI());
        localTicTacToeGameManager = gameManager;
        localTicTacToeGameManager.getGameEngine().setGameStateChangeListener(this);
        localTicTacToeGameManager.getGameEngine().getBoard().setTileEventListener(localTicTacToeGameManager);
        localTicTacToeGameManager.getBoardUI().setListener(localTicTacToeGameManager);
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
