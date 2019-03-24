package genericapp.exception;

import genericapp.AppState;

public class IllegalAppFlowItemStateException extends IllegalStateException {
    public IllegalAppFlowItemStateException(AppState caller, String oldState, String newStateAction) {
        this("Unable to " + newStateAction + " " + caller + " because it is " + oldState);
    }

    public IllegalAppFlowItemStateException(String s) {
        super(s);
    }

    public IllegalAppFlowItemStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAppFlowItemStateException(Throwable cause) {
        super(cause);
    }
}
