package tictactoe.app.state;

import genericapp.AppStateEvent;
import tictactoe.connector.event.ui.base.IGameEndStateUI;
import tictactoe.connector.event.ui.listener.GameEndStateUIListener;

public abstract class GameEndState extends AbstractTicTacToeAppState<IGameEndStateUI> implements GameEndStateUIListener {

    public static final AppStateEvent CLOSE = () -> true;

    protected GameEndState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
        getUi().setListener(this);
    }
}
