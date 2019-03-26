package tictactoe.connector.event.ui.listener;

public interface GamingStateUIListener {

    void tileClicked(int row, int col);

    void pauseGame();
}
