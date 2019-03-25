package tictactoe.app.state;

import genericapp.AppStateEvent;
import tictactoe.connector.event.ui.base.IPausedStateStateUI;
import tictactoe.connector.event.ui.listener.PausedStateUIListener;

public class PausedState extends AbstractTicTacToeAppState<IPausedStateStateUI> implements PausedStateUIListener {

    public static final AppStateEvent CLOSE = () -> true;
    public static final AppStateEvent LEAVE_GAME = () -> true;

    public PausedState(Integer id, IPausedStateStateUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }

    @Override
    public void continueGame() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }

    @Override
    public void leaveGame() {
        this.getAppStateEventHandler().handleAppStateEvent(this, LEAVE_GAME);
    }
}