package tictactoe.connector.ui.base;

import tictactoe.connector.ui.listener.GamingStateUIListener;


public interface GamingStateUI extends StateUI<GamingStateUIListener> {
    void mark(int playerNumber, int row, int col);
    void removeMark(int row, int col);
}
