package tictactoe.app.state;

import tictactoe.connector.ui.base.IServerHostingStateUI;

public class ServerHostingState extends AbstractTicTacToeAppState<IServerHostingStateUI> {
    public ServerHostingState(Integer id, IServerHostingStateUI ui) {
        super(id, ui);
    }
}
