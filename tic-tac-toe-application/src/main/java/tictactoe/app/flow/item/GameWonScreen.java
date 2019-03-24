package tictactoe.app.flow.item;

import tictactoe.connector.event.ui.base.IGameResultUI;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public class GameWonScreen extends GameEndScreen {

    public GameWonScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
