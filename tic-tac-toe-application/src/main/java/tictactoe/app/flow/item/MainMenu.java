package tictactoe.app.flow.item;

import genericapp.AbstractAppFlowItem;
import genericapp.AppFlowItemEvent;
import tictactoe.connector.event.ui.generator.MainMenuEventGenerator;
import tictactoe.connector.event.ui.listener.MainMenuListener;

public class MainMenu extends AbstractAppFlowItem implements MainMenuListener {

    public static final AppFlowItemEvent GAME_SETTINGS = () -> false;
    public static final AppFlowItemEvent START_GAME = () -> true;

    MainMenuEventGenerator mainMenuUI;

    public MainMenu(Integer id, MainMenuEventGenerator mainMenuUI) {
        super(id);
        this.mainMenuUI = mainMenuUI;
        this.mainMenuUI.setMainMenuListener(this);
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
