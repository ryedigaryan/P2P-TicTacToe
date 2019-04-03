package tictactoe.app.state;

import generic.app.AppStateEvent;
import tictactoe.connector.ui.base.MainMenuStateUI;
import tictactoe.connector.ui.listener.MainMenuStateUIListener;

public class MainMenuState extends AbstractTicTacToeAppState<MainMenuStateUI> implements MainMenuStateUIListener {

    public static final AppStateEvent GAME_SETTINGS = () -> false;
    public static final AppStateEvent START_GAME = () -> true;

    public MainMenuState(Integer id, MainMenuStateUI mainMenuUI) {
        super(id, mainMenuUI);
        getUi().setListener(this);
    }

    @Override
    public void startGame() {
        getAppStateEventHandler().handleAppStateEvent(this, START_GAME);
    }

    @Override
    public void setupGame() {
        getAppStateEventHandler().handleAppStateEvent(this, GAME_SETTINGS);
    }
}
