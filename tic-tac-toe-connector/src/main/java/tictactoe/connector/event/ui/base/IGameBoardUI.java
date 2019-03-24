package tictactoe.connector.event.ui.base;

import tictactoe.connector.event.ui.listener.TileClickListener;


public interface IGameBoardUI extends UIElementBase<TileClickListener> {
    void mark(int playerNumber, int row, int col);
    void removeMark(int row, int col);
}
