package tictactoe.connector.ui.base;

import tictactoe.connector.ui.listener.ServerHostingStateUIListener;

public interface ServerHostingStateUI extends StateUI<ServerHostingStateUIListener> {
    void foundClient(String clientName, String clientAddress);
}
