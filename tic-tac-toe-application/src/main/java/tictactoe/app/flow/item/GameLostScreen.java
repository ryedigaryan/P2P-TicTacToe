package tictactoe.app.flow.item;


import tictactoe.connector.event.ui.base.IGameResultScreen;
import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public class GameLostScreen extends GameEndScreen implements GameResultScreenListener {

    public GameLostScreen(Integer id, IGameResultScreen ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, CLOSE);
    }
}
