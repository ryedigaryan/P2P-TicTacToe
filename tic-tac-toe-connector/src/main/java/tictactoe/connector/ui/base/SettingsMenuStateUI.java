package tictactoe.connector.ui.base;

import tictactoe.connector.common.data.Settings;
import tictactoe.connector.ui.listener.SettingsMenuUIListener;

public interface SettingsMenuStateUI extends StateUI<SettingsMenuUIListener> {
    void setSettings(Settings initialSettings);
}
