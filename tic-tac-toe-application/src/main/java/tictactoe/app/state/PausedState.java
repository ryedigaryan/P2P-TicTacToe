package tictactoe.app.state;

import generic.app.AppStateEvent;
import tictactoe.connector.ui.base.PausedStateStateUI;
import tictactoe.connector.ui.listener.PausedStateUIListener;

public class PausedState extends AbstractTicTacToeAppState<PausedStateStateUI> implements PausedStateUIListener {

    public static final AppStateEvent CLOSE = () -> true;
    public static final AppStateEvent LEAVE_GAME = () -> true;

    public PausedState(Integer id, PausedStateStateUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }

    @Override
    public void continueGame() {
        getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }

    @Override
    public void leaveGame() {
        getAppStateEventHandler().handleAppStateEvent(this, LEAVE_GAME);
    }
}
