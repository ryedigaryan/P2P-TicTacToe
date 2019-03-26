package tictactoe.app.state;


import tictactoe.connector.ui.base.IGameEndStateUI;

public class GameLostState extends GameEndState {

    public GameLostState(Integer id, IGameEndStateUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        this.getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
