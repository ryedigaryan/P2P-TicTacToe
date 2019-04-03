package tictactoe.app.state;

import generic.app.AppStateEvent;
import tictactoe.connector.ui.base.GameEndStateUI;
import tictactoe.connector.ui.listener.GameEndStateUIListener;

public abstract class GameEndState extends AbstractTicTacToeAppState<GameEndStateUI> implements GameEndStateUIListener {

    public static final AppStateEvent CLOSE = () -> true;

    protected GameEndState(Integer id, GameEndStateUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }
}
