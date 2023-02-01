package demitasse.core;

/**
 * ValidationException
 */
public class ValidationException extends CoreException {
    /**
     *
     */
    public ValidationException() {
    }

    /**
     * @param message message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause   cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
}
