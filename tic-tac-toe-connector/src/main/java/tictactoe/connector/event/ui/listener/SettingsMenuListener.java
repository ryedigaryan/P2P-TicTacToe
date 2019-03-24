package tictactoe.connector.event.ui.listener;

// Move all methods to some SettingsModel class
public interface SettingsMenuListener {
    void setRowCount(int rowCount);
    void setColumnCount(int columnCount);
    void setWinLength(int winLength);
    void setPlayersCount(int playersCount);
    void backToMainMenu();
}
