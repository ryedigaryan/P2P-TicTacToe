package tictactoe.app.state;

import tictactoe.connector.ui.base.GameEndStateUI;

public class GameDrawnState extends GameEndState {

    public GameDrawnState(Integer id, GameEndStateUI ui) {
        super(id, ui);
        ui.setListener(this);
    }

    @Override
    public void close() {
        getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
