package tictactoe.connector.event.ui.generator;

import tictactoe.connector.event.ui.listener.MainMenuListener;

public interface MainMenuEventGenerator {
    void setMainMenuListener(MainMenuListener listener);

    void start();
}
