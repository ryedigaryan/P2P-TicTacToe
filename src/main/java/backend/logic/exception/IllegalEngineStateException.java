package backend.logic.exception;

public class IllegalEngineStateException extends IllegalStateException {
    public IllegalEngineStateException(int winnerNumber) {
        super("Game has a winner: " + winnerNumber);
    }

    public IllegalEngineStateException(String s) {
        super(s);
    }

    public IllegalEngineStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEngineStateException(Throwable cause) {
        super(cause);
    }
}
