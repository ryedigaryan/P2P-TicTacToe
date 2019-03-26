package tictactoe.connector.backend.listener;

public interface GameStateChangeListener {

    void playerWon(int playerNumber);

    void playerLost(int playerNumber);

    void draw();
}
