package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.base.IPausedScreenUI;
import tictactoe.connector.event.ui.listener.PausedScreenListener;

public class PauseScreen extends AbstractTicTacToeAppFlowItem<IPausedScreenUI> implements PausedScreenListener {

    public static final AppFlowItemEvent CLOSE = () -> true;
    public static final AppFlowItemEvent LEAVE_GAME = () -> true;

    public PauseScreen(Integer id, IPausedScreenUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }

    @Override
    public void continueGame() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }

    @Override
    public void leaveGame() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, LEAVE_GAME);
    }
}
