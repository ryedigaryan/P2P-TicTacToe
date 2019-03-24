package tictactoe.app.state;


import tictactoe.connector.event.ui.base.IGameResultUI;

public class GameLostState extends GameEndState {

    public GameLostState(Integer id, IGameResultUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
