package demitasse.core;

/**
 * CoreException
 */
public class CoreException extends RuntimeException {
    /**
     *
     */
    public CoreException() {
    }

    /**
     * @param message message
     */
    public CoreException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause   cause
     */
    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public CoreException(Throwable cause) {
        super(cause);
    }
}
