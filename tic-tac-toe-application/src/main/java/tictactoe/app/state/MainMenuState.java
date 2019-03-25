package tictactoe.app.state;

import genericapp.AppStateEvent;
import tictactoe.connector.event.ui.base.IMainMenuStateUI;
import tictactoe.connector.event.ui.listener.MainMenuStateUIListener;

public class MainMenuState extends AbstractTicTacToeAppState<IMainMenuStateUI> implements MainMenuStateUIListener {

    public static final AppStateEvent GAME_SETTINGS = () -> false;
    public static final AppStateEvent START_GAME = () -> true;

    public MainMenuState(Integer id, IMainMenuStateUI mainMenuUI) {
        super(id, mainMenuUI);
        getUi().setListener(this);
    }

    @Override
    public void startGame() {
        this.getAppStateEventHandler().handleAppStateEvent(this, START_GAME);
    }

    @Override
    public void setupGame() {
        this.getAppStateEventHandler().handleAppStateEvent(this, GAME_SETTINGS);
    }
}