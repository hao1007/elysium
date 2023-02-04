package demitasse.sys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static demitasse.sys.Sys.sys;
import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link Sys}
 */
public final class SysTest {
    private Sys sys;

    @BeforeEach
    public void beforeEach() {
        sys = sys();
    }

    @Test
    public void test() {
        assertEquals("demitasse.sys.Sys$DefaultSys", sys.getClass().getName());
    }

    @Test
    public void test_millis() {
        long t0 = currentTimeMillis();
        long t = sys.millis();
        long t1 = currentTimeMillis();
        assertTrue(t0 <= t);
        assertTrue(t <= t1);
    }
}
