package tictactoe.app.flow.item;

import genericapp.AppStateEvent;
import tictactoe.connector.event.ui.base.IMainMenuUI;
import tictactoe.connector.event.ui.listener.MainMenuListener;

public class MainMenu extends AbstractTicTacToeAppState<IMainMenuUI> implements MainMenuListener {

    public static final AppStateEvent GAME_SETTINGS = () -> false;
    public static final AppStateEvent START_GAME = () -> true;

    public MainMenu(Integer id, IMainMenuUI mainMenuUI) {
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
