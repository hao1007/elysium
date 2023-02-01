package demitasse.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static demitasse.core.CoreTools.defaultValue;
import static demitasse.core.CoreTools.isEmpty;
import static demitasse.core.CoreTools.predicateFalse;
import static demitasse.core.CoreTools.predicateTrue;
import static demitasse.core.CoreTools.toClass;
import static demitasse.core.CoreTools.type;
import static demitasse.core.CoreTools.validate;
import static java.lang.Boolean.FALSE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link CoreTools}
 */
public final class CoreToolsTest {
    private Object object;
    private String message;

    @BeforeEach
    public void beforeEach() {
        object = new Object();
        message = "message";
    }

    @Test
    public void test_predicateTrue() {
        assertTrue(predicateTrue().test(null));
    }

    @Test
    public void test_predicateFalse() {
        assertFalse(predicateFalse().test(null));
    }

    @Test
    public void test_isEmpty_collection() {
        assertTrue(isEmpty((Collection<?>) null));
        assertTrue(isEmpty(Set.of()));
        assertFalse(isEmpty(Set.of(object)));
    }

    @Test
    public void test_isEmpty_map() {
        assertTrue(isEmpty((Map<?, ?>) null));
        assertTrue(isEmpty(Map.of()));
        assertFalse(isEmpty(Map.of(object, object)));
    }

    @Test
    public void test_isEmpty_string() {
        assertTrue(isEmpty((String) null));
        assertTrue(isEmpty(""));
        assertFalse(isEmpty(" "));
    }

    @Test
    public void test_isBlank() {
        assertTrue(CoreTools.isBlank(null));
        assertTrue(CoreTools.isBlank(""));
        assertTrue(CoreTools.isBlank(" "));
        assertFalse(CoreTools.isBlank(message));
    }

    @Test
    public void test_validate_message() {
        assertEquals(object, validate(object, o -> true, (String) null));
        assertEquals(message, assertThrows(ValidationException.class, () -> validate(object, o -> false, message)).getMessage());
        assertEquals("invalid object", assertThrows(ValidationException.class, () -> validate(object, o -> false, (String) null)).getMessage());
    }

    @Test
    public void test_validate_supplier() {
        assertEquals(object, validate(object, o -> true, () -> null));
        assertEquals(message, assertThrows(ValidationException.class, () -> validate(object, o -> false, () -> message)).getMessage());
        assertEquals("invalid object", assertThrows(ValidationException.class, () -> validate(null, o -> false, (Supplier<String>) null)).getMessage());
    }

    @Test
    public void test_validate_function() {
        assertEquals(object, validate(object, null, o -> null));
        assertEquals(object, validate(object, o -> true, o -> null));
        assertEquals(message, assertThrows(RuntimeException.class, () -> validate(object, o -> false, o -> new RuntimeException(message))).getMessage());
        assertEquals("invalid object", assertThrows(ValidationException.class, () -> validate(null, o -> false, (Function<? super Object, ? extends Throwable>) null)).getMessage());
    }

    @Test
    public void test_defaultValue() {
        assertEquals(FALSE, defaultValue(boolean.class));
        assertEquals(Byte.valueOf((byte) 0), defaultValue(byte.class));
        assertEquals(Character.valueOf('\u0000'), defaultValue(char.class));
        assertEquals(Short.valueOf((short) 0), defaultValue(short.class));
        assertEquals(Integer.valueOf(0), defaultValue(int.class));
        assertEquals(Long.valueOf(0L), defaultValue(long.class));
        assertEquals(Float.valueOf(0.0f), defaultValue(float.class));
        assertEquals(Double.valueOf(0.0), defaultValue(double.class));
        assertNull(defaultValue(String.class));
        assertArrayEquals(new Object[0], defaultValue(Object[].class));
    }

    @Test
    public void test_type() {
        assertEquals("blank name", assertThrows(ValidationException.class, () -> type(null)).getMessage());
        assertEquals(Object.class, type("java.lang.Object"));
    }

    @Test
    public void test_toClass() {
        assertNull(toClass(null));
        assertNull(toClass(""));
        assertEquals(Object.class, toClass(Object.class));
        assertEquals(Object.class, toClass("java.lang.Object"));
        assertEquals(int.class, toClass(int.class.getName()));

        assertEquals("java.lang.ClassNotFoundException: 0", assertThrows(CoreException.class, () -> toClass("0")).getMessage());
    }
}
