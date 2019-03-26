package tictactoe.backend.manager;

import tictactoe.backend.logic.GameEngine;
import tictactoe.connector.event.backend.listener.TileEventListener;
import tictactoe.connector.event.ui.base.IGamingStateUI;
import tictactoe.connector.event.ui.listener.GamingStateUIListener;

public interface GameManager<UIType extends IGamingStateUI> extends TileEventListener, GamingStateUIListener {

    GameEngine getGameEngine();

    String getWinnerName();

    String getPlayerName();

    UIType getUI();
}
