package backend.logic.exception;

public class TileOutOfBoardException extends Exception {
    public TileOutOfBoardException() {
    }

    public TileOutOfBoardException(String message) {
        super(message);
    }

    public TileOutOfBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public TileOutOfBoardException(Throwable cause) {
        super(cause);
    }

    public TileOutOfBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
