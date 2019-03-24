package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import lombok.Getter;
import tictactoe.backend.manager.LocalGameManager;
import tictactoe.connector.event.backend.listener.GameStateChangeListener;
import tictactoe.connector.event.ui.base.IGameBoardUI;

@Getter
public class GamingScene extends AbstractTicTacToeAppFlowItem<IGameBoardUI> implements GameStateChangeListener {

    public static final AppFlowItemEvent PAUSE = () -> false;
    public static final AppFlowItemEvent GAME_WON = () -> true;
    public static final AppFlowItemEvent GAME_LOST = () -> true;

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
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, GAME_WON);
    }
}
