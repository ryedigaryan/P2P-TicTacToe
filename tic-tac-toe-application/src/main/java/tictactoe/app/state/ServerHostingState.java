package tictactoe.app.state;

import tictactoe.connector.ui.base.ServerHostingStateUI;

public class ServerHostingState extends AbstractTicTacToeAppState<ServerHostingStateUI> {
    public ServerHostingState(Integer id, ServerHostingStateUI ui) {
        super(id, ui);
    }
}
