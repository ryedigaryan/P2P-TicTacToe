package tictactoe.connector.event.ui.base;

import tictactoe.connector.event.ui.listener.TileClickListener;


public interface IGameBoardUI extends UIElementBase<TileClickListener> {

    // TODO: 3/20/2019 Move these methods to listener
    void markO(int row, int col);
    void markX(int row, int col);
    void removeMark(int row, int col);
}
