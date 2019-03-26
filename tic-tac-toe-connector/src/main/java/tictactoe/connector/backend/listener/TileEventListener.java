package tictactoe.connector.backend.listener;

public interface TileEventListener {

    void valueChanged(int row, int col, int oldValue, int newValue);

    void valueErased(int row, int col, int oldValue);
}
