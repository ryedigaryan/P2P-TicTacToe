package genericapp.exception;

import genericapp.AppFlowItem;

public class IllegalAppFlowItemStateException extends IllegalStateException {
    public IllegalAppFlowItemStateException(AppFlowItem caller, String oldState, String newStateAction) {
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
