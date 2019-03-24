package tictactoe.app.state;

import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameDrawnState extends GameEndState {

    public GameDrawnState(Integer id, IGameResultUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
