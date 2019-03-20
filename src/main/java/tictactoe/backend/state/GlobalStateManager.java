package tictactoe.backend.state;

import tictactoe.backend.state.controller.MainMenuController;
import tictactoe.backend.state.listener.GlobalStateChangeHandler;
import tictactoe.ui.state.listener.MainMenuListener;

import java.util.LinkedList;

public class GlobalStateManager implements GlobalStateChangeHandler {
    LinkedList<GlobalState> stateStack;

    public GlobalStateManager() {
        stateStack = new LinkedList<>();
        stateStack.push(GlobalState.MAIN_MENU);
//        new MainMenuController();
    }

    @Override
    public void changeGlobalState(GlobalState oldState, GlobalState newState) {
        switch (newState) {
            case MAIN_MENU:
                // this should be done with help of discardGlobalState() function
                assert false : "No one should acquire to change state to MAIN_MENU";
                break;
            case GAME_SETUP:
                assert oldState == GlobalState.MAIN_MENU : "For now only MAIN_MENU can call GAME_SETUP";

                break;
            case GAMING:
                assert oldState == GlobalState.MAIN_MENU : "For now only MAIN_MENU can call GAME_SETUP";
                break;
            case PAUSED:
                assert oldState == GlobalState.GAMING : "For now only GAMING can call PAUSED";
                break;
            case GAME_WON:
                assert oldState == GlobalState.GAMING : "For now only GAMING can call GAME_WON";
                break;
            case GAME_LOST:
                assert oldState == GlobalState.GAMING : "For now only GAMING can call GAME_LOST";
                break;
        }
    }

    @Override
    public void discardGlobalState() {

    }

    @Override
    public void processGlobalState() {

    }
}
