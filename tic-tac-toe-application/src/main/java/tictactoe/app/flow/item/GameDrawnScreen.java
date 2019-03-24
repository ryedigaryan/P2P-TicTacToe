package tictactoe.app.flow.item;

import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameDrawnScreen extends GameEndScreen {

    public GameDrawnScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
