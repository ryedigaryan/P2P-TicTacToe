package tictactoe.connector.event.generator;

import tictactoe.connector.event.listener.TileClickListener;

public interface BoardEventGenerator {
    void setTileClickListener(TileClickListener listener);

    // TODO: 3/20/2019 Move these methods to listener
    void markO(int row, int col);
    void markX(int row, int col);
    void removeMark(int row, int col);
}
