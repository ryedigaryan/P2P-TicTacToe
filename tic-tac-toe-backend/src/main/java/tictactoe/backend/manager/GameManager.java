package tictactoe.backend.manager;

import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.backend.listener.TileEventListener;
import tictactoe.connector.ui.base.IGamingStateUI;

public interface GameManager<UIType extends IGamingStateUI> extends TileEventListener {

    GameEngine getGameEngine();

    String getWinnerName();

    String getPlayerName();

    UIType getUI();

    void processPlayerInput(int row, int col);
}
