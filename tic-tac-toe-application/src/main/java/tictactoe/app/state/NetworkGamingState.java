package tictactoe.app.state;

import tictactoe.connector.ui.base.IGamingStateUI;

public class NetworkGamingState<UIType extends IGamingStateUI> extends GamingState<UIType> {
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
    public void valueChanged(int row, int col, int oldValue, int newValue) {

    }

    @Override
    public void valueErased(int row, int col, int oldValue) {

    }

    @Override
    public void tileClicked(int row, int col) {

    }

    @Override
    public void pauseGame() {

    }
}
