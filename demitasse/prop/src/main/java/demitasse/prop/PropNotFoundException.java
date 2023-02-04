package demitasse.prop;

import demitasse.core.CoreException;

/**
 * PropNotFoundException
 */
public class PropNotFoundException extends CoreException {
    /**
     *
     */
    public PropNotFoundException() {
    }

    /**
     * @param message message
     */
    public PropNotFoundException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause   cause
     */
    public PropNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public PropNotFoundException(Throwable cause) {
        super(cause);
    }
}
