package tictactoe.backend.state;

import java.util.LinkedList;

public class GlobalStateMachine {
    LinkedList<GlobalState> globalStateStack;

    public GlobalStateMachine() {
        globalStateStack = new LinkedList<>();
        globalStateStack.add(GlobalState.MAIN_MENU);
    }

    public GlobalState getState() {
        return globalStateStack.peek();
    }

    public void pushNextState() {
    }
}
