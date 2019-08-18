package test.app.simpleworkflow.impl.restriction;

public class RestrictionException extends RuntimeException {

    public RestrictionException() {
    }

    public RestrictionException(String message) {
        super(message);
    }

    public RestrictionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestrictionException(Throwable cause) {
        super(cause);
    }

    public RestrictionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
