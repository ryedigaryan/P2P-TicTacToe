package tictactoe.connector.event.ui.base;

import tictactoe.connector.event.ui.listener.GamingStateUIListener;


public interface IGamingStateUI extends StateUI<GamingStateUIListener> {
    void mark(int playerNumber, int row, int col);
    void removeMark(int row, int col);
}
