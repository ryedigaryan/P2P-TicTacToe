package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.base.IGameResultUI;

public abstract class GameEndScreen extends AbstractTicTacToeAppFlowItem<IGameResultUI> {

    public static final AppFlowItemEvent CLOSE = () -> true;

    protected GameEndScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
    }
}
