package demitasse.core;

import demitasse.core.ThrowableFixture.DefaultThrowables;
import org.junit.jupiter.api.Test;

import static demitasse.core.ThrowableFixture.verify;

/**
 * {@link ValidationException}
 */
public final class ValidationExceptionTest {
    @Test
    public void test() {
        verify((m, c) -> new DefaultThrowables<>(new ValidationException(), new ValidationException(m), new ValidationException(c), new ValidationException(m, c)));
    }
}
