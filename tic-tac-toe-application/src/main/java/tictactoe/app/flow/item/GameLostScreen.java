package tictactoe.app.flow.item;


import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameLostScreen extends GameEndScreen {

    public GameLostScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
