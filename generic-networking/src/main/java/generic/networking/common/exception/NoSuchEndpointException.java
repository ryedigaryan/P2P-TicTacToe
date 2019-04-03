package generic.networking.common.exception;

import java.net.InetSocketAddress;

public class NoSuchEndpointException extends RuntimeException {
    public NoSuchEndpointException(InetSocketAddress endpointAddress) {
        this("No such endpoint: " + endpointAddress);
    }

    public NoSuchEndpointException(String message) {
        super(message);
    }

    public NoSuchEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEndpointException(Throwable cause) {
        super(cause);
    }

    public NoSuchEndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
