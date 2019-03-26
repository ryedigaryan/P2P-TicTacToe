package tictactoe.app.state;

import tictactoe.connector.ui.base.IGameEndStateUI;

public class GameDrawnState extends GameEndState {

    public GameDrawnState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
