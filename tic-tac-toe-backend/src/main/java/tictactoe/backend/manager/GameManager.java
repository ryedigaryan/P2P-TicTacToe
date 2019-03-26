package tictactoe.backend.manager;

import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.backend.listener.TileEventListener;
import tictactoe.connector.ui.base.IGamingStateUI;
import tictactoe.connector.ui.listener.GamingStateUIListener;

public interface GameManager<UIType extends IGamingStateUI> extends TileEventListener, GamingStateUIListener {

    GameEngine getGameEngine();

    String getWinnerName();

    String getPlayerName();

    UIType getUI();
}
