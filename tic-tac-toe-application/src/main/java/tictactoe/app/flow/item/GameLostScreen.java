package tictactoe.app.flow.item;


import tictactoe.connector.event.ui.base.IGameResultUI;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public class GameLostScreen extends GameEndScreen {

    public GameLostScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
