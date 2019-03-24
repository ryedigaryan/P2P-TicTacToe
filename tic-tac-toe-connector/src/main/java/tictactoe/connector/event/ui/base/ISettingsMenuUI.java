package tictactoe.connector.event.ui.base;

import tictactoe.connector.event.ui.listener.SettingsMenuListener;

public interface ISettingsMenuUI extends UIElementBase {
    void setListener(SettingsMenuListener listener);
}
