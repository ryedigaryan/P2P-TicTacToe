package tictactoe.app.state;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tictactoe.connector.backend.listener.GameStateChangeListener;
import tictactoe.connector.backend.listener.TileEventListener;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public abstract class GamingState<UIType extends IGamingStateUI> extends AbstractTicTacToeAppState<UIType> implements GameStateChangeListener, GamingStateUIListener, TileEventListener {
    private int playerNumber;

    public GamingState(Integer id, UIType ui) {
        super(id, ui);
    }
}
