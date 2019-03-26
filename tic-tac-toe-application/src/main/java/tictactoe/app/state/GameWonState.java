package tictactoe.app.state;

import tictactoe.connector.ui.base.IGameEndStateUI;

public class GameWonState extends GameEndState {

    public GameWonState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
