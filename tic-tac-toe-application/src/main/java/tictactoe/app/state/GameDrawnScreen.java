package tictactoe.app.state;

import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameDrawnScreen extends GameEndScreen {

    public GameDrawnScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
