package tictactoe.app.state;

import tictactoe.connector.ui.base.GameEndStateUI;

public class GameWonState extends GameEndState {

    public GameWonState(Integer id, GameEndStateUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
