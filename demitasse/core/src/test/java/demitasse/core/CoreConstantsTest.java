package demitasse.core;

import org.junit.jupiter.api.Test;

import static demitasse.core.CoreConstants.EMPTY;
import static demitasse.core.CoreConstants.EMPTY_CLASSES;
import static demitasse.core.CoreConstants.EMPTY_OBJECTS;
import static demitasse.core.CoreConstants.EMPTY_STRINGS;
import static demitasse.core.CoreConstants.UNSPECIFIED;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link CoreConstants}
 */
public final class CoreConstantsTest {
    @Test
    public void test() {
        assertEquals("__UNSPECIFIED__", UNSPECIFIED);
        assertArrayEquals(new Object[0], EMPTY_OBJECTS);
        assertArrayEquals(new Class<?>[0], EMPTY_CLASSES);
        assertArrayEquals(new String[0], EMPTY_STRINGS);
        assertEquals("", EMPTY);
    }
}
