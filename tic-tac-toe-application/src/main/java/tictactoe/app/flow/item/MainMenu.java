package tictactoe.app.flow.item;

import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.base.IMainMenuUI;
import tictactoe.connector.event.ui.listener.MainMenuListener;

public class MainMenu extends AbstractTicTacToeAppFlowItem<IMainMenuUI> implements MainMenuListener {

    public static final AppFlowItemEvent GAME_SETTINGS = () -> false;
    public static final AppFlowItemEvent START_GAME = () -> true;

    public MainMenu(Integer id, IMainMenuUI mainMenuUI) {
        super(id, mainMenuUI);
        getUi().setListener(this);
    }

    @Override
    public void startGame() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, START_GAME);
    }

    @Override
    public void setupGame() {
        getAppFlowItemEventHandler().handleAppFlowItemEvent(this, GAME_SETTINGS);
    }
}
