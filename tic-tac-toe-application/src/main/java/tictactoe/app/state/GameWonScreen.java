package tictactoe.app.state;

import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameWonScreen extends GameEndScreen {

    public GameWonScreen(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
