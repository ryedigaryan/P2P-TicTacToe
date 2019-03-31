package tictactoe.app.state;

import tictactoe.connector.backend.listener.GameStateChangeListener;
import tictactoe.connector.backend.listener.TileEventListener;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

public abstract class GamingState<UIType extends IGamingStateUI> extends AbstractTicTacToeAppState<UIType> implements GameStateChangeListener, GamingStateUIListener, TileEventListener {
    public GamingState(Integer id, UIType ui) {
        super(id, ui);
    }
}
