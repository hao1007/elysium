package demitasse.core;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * ThrowableFixture
 */
public final class ThrowableFixture {
    private static final String MESSAGE = "message";
    private static final Throwable CAUSE = new Throwable("cause");

    /**
     * @param function function
     */
    public static <T extends Throwable> void verify(BiFunction<String, Throwable, Throwables<T>> function) {
        Throwables<T> throwables = function.apply(MESSAGE, CAUSE);
        assertNotNull(throwables);
        if (throwables.hasEmpty()) {
            T throwable = throwables.empty();
            assertNull(throwable.getMessage());
            assertNull(throwable.getCause());
        }
        if (throwables.hasMessage()) {
            T throwable = throwables.message();
            assertEquals(MESSAGE, throwable.getMessage());
            assertNull(throwable.getCause());
        }
        if (throwables.hasCause()) {
            T throwable = throwables.cause();
            assertEquals(String.valueOf(CAUSE), throwable.getMessage());
            assertEquals(CAUSE, throwable.getCause());
        }
        if (throwables.hasMessageCause()) {
            T throwable = throwables.messageCause();
            assertEquals(MESSAGE, throwable.getMessage());
            assertEquals(CAUSE, throwable.getCause());
        }
    }

    /**
     * Throwables
     */
    public interface Throwables<T extends Throwable> {
        boolean hasEmpty();

        boolean hasMessage();

        boolean hasCause();

        boolean hasMessageCause();

        T empty();

        T message();

        T cause();

        T messageCause();
    }

    /**
     * DefaultThrowables
     */
    public static final class DefaultThrowables<T extends Throwable> implements Throwables<T> {
        private final T empty;
        private final T message;
        private final T cause;
        private final T message_cause;

        /**
         * @param empty         empty
         * @param message       message
         * @param cause         cause
         * @param message_cause message cause
         */
        public DefaultThrowables(T empty, T message, T cause, T message_cause) {
            this.empty = empty;
            this.message = message;
            this.cause = cause;
            this.message_cause = message_cause;
        }

        @Override
        public boolean hasEmpty() {
            return empty != null;
        }

        @Override
        public boolean hasMessage() {
            return message != null;
        }

        @Override
        public boolean hasCause() {
            return cause != null;
        }

        @Override
        public boolean hasMessageCause() {
            return message_cause != null;
        }

        @Override
        public T empty() {
            return empty;
        }

        @Override
        public T message() {
            return message;
        }

        @Override
        public T cause() {
            return cause;
        }

        @Override
        public T messageCause() {
            return message_cause;
        }
    }
}
