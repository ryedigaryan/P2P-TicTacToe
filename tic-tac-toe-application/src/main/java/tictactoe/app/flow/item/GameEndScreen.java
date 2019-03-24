package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.base.IGameResultUI;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public abstract class GameEndScreen extends AbstractTicTacToeAppState<IGameResultUI> implements GameResultScreenListener {

    public static final AppFlowItemEvent CLOSE = () -> true;

    protected GameEndScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }
}
