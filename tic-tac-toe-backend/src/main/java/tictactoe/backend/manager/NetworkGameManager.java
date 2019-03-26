package tictactoe.backend.manager;

import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.ui.base.IGamingStateUI;

/**
 * A game manager which is designed for game through internet.
 */
public class NetworkGameManager<UIType extends IGamingStateUI> implements GameManager<UIType> {
    @Override
    public GameEngine getGameEngine() {
        return null;
    }

    @Override
    public String getWinnerName() {
        return null;
    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public UIType getUI() {
        return null;
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
