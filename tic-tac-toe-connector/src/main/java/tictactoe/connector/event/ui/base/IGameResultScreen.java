package tictactoe.connector.event.ui.base;

import tictactoe.connector.event.ui.listener.GameResultScreenListener;

public interface IGameResultScreen extends UIElementBase {
    void setListener(GameResultScreenListener listener);
}
