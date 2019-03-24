package tictactoe.app.flow.item;

import tictactoe.connector.event.ui.base.IGameResultUI;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public class GameWonScreen extends GameEndScreen implements GameResultScreenListener {

    public GameWonScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
