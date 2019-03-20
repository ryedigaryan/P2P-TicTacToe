package tictactoe.backend.state.listener;

import tictactoe.backend.state.GlobalState;

public interface GlobalStateChangeHandler {

    void changeGlobalState(GlobalState oldState, GlobalState newState);

    void discardGlobalState();

    void processGlobalState();
}
