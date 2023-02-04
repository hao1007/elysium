package demitasse.prop;

import demitasse.core.ThrowableFixture.DefaultThrowables;
import org.junit.jupiter.api.Test;

import static demitasse.core.ThrowableFixture.verify;

/**
 * {@link PropNotFoundException}
 */
public final class PropNotFoundExceptionTest {
    @Test
    public void test() {
        verify((m, c) -> new DefaultThrowables<>(new PropNotFoundException(), new PropNotFoundException(m), new PropNotFoundException(c), new PropNotFoundException(m, c)));
    }
}
