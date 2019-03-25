package tictactoe.app.state;

import tictactoe.connector.event.ui.base.IGameEndStateUI;

public class GameDrawnState extends GameEndState {

    public GameDrawnState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}