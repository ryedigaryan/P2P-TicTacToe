package tictactoe.connector.ui.listener;

// Move all methods to some SettingsModel class
public interface SettingsMenuUIListener {
    void setRowCount(int rowCount);
    void setColumnCount(int columnCount);
    void setWinLength(int winLength);
    void setPlayersCount(int playersCount);
    void close(boolean saveChanges);
}
