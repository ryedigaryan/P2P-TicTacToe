package generic.app.exception;

import generic.app.AppState;

public class IllegalAppStateLifecycleException extends IllegalStateException {
    public IllegalAppStateLifecycleException(AppState caller, String oldState, String newStateAction) {
        this("Unable to " + newStateAction + " " + caller + " because it is " + oldState);
    }

    public IllegalAppStateLifecycleException(String s) {
        super(s);
    }

    public IllegalAppStateLifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAppStateLifecycleException(Throwable cause) {
        super(cause);
    }
}
