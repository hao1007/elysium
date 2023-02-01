package demitasse.core;

import demitasse.core.ThrowableFixture.DefaultThrowables;
import org.junit.jupiter.api.Test;

import static demitasse.core.ThrowableFixture.verify;

/**
 * {@link CoreException}
 */
public final class CoreExceptionTest {
    @Test
    public void test() {
        verify((m, c) -> new DefaultThrowables<>(new CoreException(), new CoreException(m), new CoreException(c), new CoreException(m, c)));
    }
}
