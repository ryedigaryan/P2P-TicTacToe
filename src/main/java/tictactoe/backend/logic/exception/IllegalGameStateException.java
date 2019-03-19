package tictactoe.backend.logic.exception;

public class IllegalGameStateException extends IllegalStateException {
    public IllegalGameStateException(int winnerNumber) {
        super("Game has a winner: " + winnerNumber);
    }

    public IllegalGameStateException(String s) {
        super(s);
    }

    public IllegalGameStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalGameStateException(Throwable cause) {
        super(cause);
    }
}
