package tictactoe.app.state;

import tictactoe.connector.backend.listener.GameStateChangeListener;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

public class NetworkGamingState<UIType extends IGamingStateUI> extends AbstractTicTacToeAppState<UIType> implements GameStateChangeListener, GamingStateUIListener {
    public NetworkGamingState(Integer id, UIType ui) {
        super(id, ui);
    }

    @Override
    public void playerWon(int playerNumber) {

    }

    @Override
    public void playerLost(int playerNumber) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void tileClicked(int row, int col) {

    }

    @Override
    public void pauseGame() {

    }
}
