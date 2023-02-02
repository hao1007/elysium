package demitasse.core;

import org.junit.jupiter.api.Test;

import static demitasse.core.CoreConstants.BRACE_CLOSE;
import static demitasse.core.CoreConstants.BRACE_OPEN;
import static demitasse.core.CoreConstants.BRACKET_CLOSE;
import static demitasse.core.CoreConstants.BRACKET_OPEN;
import static demitasse.core.CoreConstants.COMMA;
import static demitasse.core.CoreConstants.EMPTY;
import static demitasse.core.CoreConstants.EMPTY_CLASSES;
import static demitasse.core.CoreConstants.EMPTY_OBJECTS;
import static demitasse.core.CoreConstants.EMPTY_STRINGS;
import static demitasse.core.CoreConstants.EQUAL;
import static demitasse.core.CoreConstants.HYPHEN;
import static demitasse.core.CoreConstants.INSTANCE_ENUM;
import static demitasse.core.CoreConstants.INSTANCE_FIELD;
import static demitasse.core.CoreConstants.INSTANCE_METHOD;
import static demitasse.core.CoreConstants.NULL;
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
        assertEquals("null", NULL);
        assertEquals("Instance", INSTANCE_ENUM);
        assertEquals("INSTANCE", INSTANCE_FIELD);
        assertEquals("instance", INSTANCE_METHOD);
        assertEquals("{", BRACE_OPEN);
        assertEquals("}", BRACE_CLOSE);
        assertEquals("[", BRACKET_OPEN);
        assertEquals("]", BRACKET_CLOSE);
        assertArrayEquals(new Object[0], EMPTY_OBJECTS);
        assertArrayEquals(new Class<?>[0], EMPTY_CLASSES);
        assertArrayEquals(new String[0], EMPTY_STRINGS);
        assertEquals("", EMPTY);
        assertEquals("=", EQUAL);
        assertEquals("-", HYPHEN);
        assertEquals(",", COMMA);
    }
}
