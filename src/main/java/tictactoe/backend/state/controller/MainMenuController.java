package tictactoe.backend.state.controller;

import tictactoe.backend.state.GlobalState;
import tictactoe.ui.state.MainMenuUI;
import tictactoe.ui.state.listener.MainMenuListener;

public class MainMenuController extends ControllerBase implements MainMenuListener {

    public MainMenuController(MainMenuUI ui) {
        ui.setMainMenuListener(this);
    }

    @Override
    public void startGame() {
        globalStateChangeHandler.changeGlobalState(GlobalState.MAIN_MENU, GlobalState.GAMING);
    }

    @Override
    public void setupGame() {
        globalStateChangeHandler.changeGlobalState(GlobalState.MAIN_MENU, GlobalState.GAME_SETUP);
    }
}
