package tictactoe.app.flow.item;

import genericapp.AppStateEvent;
import tictactoe.connector.event.ui.base.IPausedScreenUI;
import tictactoe.connector.event.ui.listener.PausedScreenListener;

public class PauseScreen extends AbstractTicTacToeAppState<IPausedScreenUI> implements PausedScreenListener {

    public static final AppStateEvent CLOSE = () -> true;
    public static final AppStateEvent LEAVE_GAME = () -> true;

    public PauseScreen(Integer id, IPausedScreenUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }

    @Override
    public void continueGame() {
        this.getAppStateEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }

    @Override
    public void leaveGame() {
        this.getAppStateEventHandler().handleAppFlowItemEvent(this, LEAVE_GAME);
    }
}
