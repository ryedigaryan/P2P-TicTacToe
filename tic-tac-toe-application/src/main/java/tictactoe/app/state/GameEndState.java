package tictactoe.app.state;

import generic.app.AppStateEvent;
import tictactoe.connector.ui.base.IGameEndStateUI;
import tictactoe.connector.ui.listener.GameEndStateUIListener;

public abstract class GameEndState extends AbstractTicTacToeAppState<IGameEndStateUI> implements GameEndStateUIListener {

    public static final AppStateEvent CLOSE = () -> true;

    protected GameEndState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }
}
