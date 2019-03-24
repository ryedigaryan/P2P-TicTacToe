package tictactoe.app.state;

import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameWonState extends GameEndState {

    public GameWonState(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
