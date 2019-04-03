package tictactoe.app.state;


import tictactoe.connector.ui.base.GameEndStateUI;

public class GameLostState extends GameEndState {

    public GameLostState(Integer id, GameEndStateUI ui) {
        super(id, ui);
    }

    @Override
    public void close() {
        getAppStateEventHandler().handleAppStateEvent(this, CLOSE);
    }
}
