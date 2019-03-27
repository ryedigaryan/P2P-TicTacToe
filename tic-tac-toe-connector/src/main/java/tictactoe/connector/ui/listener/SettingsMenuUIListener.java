package tictactoe.connector.ui.listener;

import tictactoe.connector.common.data.Settings;

// Move all methods to some SettingsModel class
public interface SettingsMenuUIListener {
    void setGameSettings(Settings settings);
    void close(boolean saveChanges);
}
