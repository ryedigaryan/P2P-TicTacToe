package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.base.IGameResultScreen;

public abstract class GameEndScreen extends AbstractTicTacToeAppFlowItem<IGameResultScreen> {

    public static final AppFlowItemEvent CLOSE = () -> true;

    protected GameEndScreen(Integer id, IGameResultScreen ui) {
        super(id, ui);
    }
}
