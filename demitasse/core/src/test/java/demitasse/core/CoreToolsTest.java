package demitasse.core;

import demitasse.core.CoreTools.DefaultInspectionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static demitasse.core.CoreTools.defaultValue;
import static demitasse.core.CoreTools.falsePredicate;
import static demitasse.core.CoreTools.field;
import static demitasse.core.CoreTools.inspect;
import static demitasse.core.CoreTools.instance;
import static demitasse.core.CoreTools.isEmpty;
import static demitasse.core.CoreTools.method;
import static demitasse.core.CoreTools.object;
import static demitasse.core.CoreTools.suppressAccessControl;
import static demitasse.core.CoreTools.toBoolean;
import static demitasse.core.CoreTools.toByte;
import static demitasse.core.CoreTools.toCharacter;
import static demitasse.core.CoreTools.toClass;
import static demitasse.core.CoreTools.toDouble;
import static demitasse.core.CoreTools.toFloat;
import static demitasse.core.CoreTools.toInteger;
import static demitasse.core.CoreTools.toLong;
import static demitasse.core.CoreTools.toShort;
import static demitasse.core.CoreTools.truePredicate;
import static demitasse.core.CoreTools.type;
import static demitasse.core.CoreTools.validate;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * {@link CoreTools}
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"ConstantValue", "unused"})
public final class CoreToolsTest {
    @Mock
    private AccessibleObject accessible_object;
    private Object object;
    private String message;
    private Class<?> type;

    @BeforeEach
    public void beforeEach() {
        object = new Object();
        message = "message";
        type = Object.class;
    }

    @Test
    public void test_truePredicate() {
        assertTrue(truePredicate().test(null));
    }

    @Test
    public void test_falsePredicate() {
        assertFalse(falsePredicate().test(null));
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
    public void test_suppressAccessControl() {
        suppressAccessControl(null);
        suppressAccessControl(accessible_object);
        verify(accessible_object).setAccessible(true);
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
        assertEquals(type, type(type.getName()));
    }

    @Test
    public void test_toClass() {
        assertNull(toClass(null));
        assertNull(toClass(""));
        assertEquals(Object.class, toClass(Object.class));
        assertEquals(type, toClass(type.getName()));
        assertEquals(int.class, toClass(int.class.getName()));

        assertEquals(ClassNotFoundException.class.getName() + ": 0", assertThrows(CoreException.class, () -> toClass("0")).getMessage());
    }

    @Test
    public void test_toString() {
        assertNull(CoreTools.toString(null));
        assertEquals(String.valueOf(object), CoreTools.toString(object));
    }

    @Test
    public void test_toBoolean() {
        assertNull(toBoolean(null));
        assertTrue(toBoolean(TRUE));
        assertFalse(toBoolean(""));
        assertTrue(toBoolean("TrUe"));
    }

    @Test
    public void test_toByte() {
        assertNull(toByte(null));
        assertNull(toByte(""));
        assertEquals((byte) 1, toByte((byte) 1));
        assertEquals((byte) 1, toByte(1));
        assertEquals((byte) 1, toByte("1"));
    }

    @Test
    public void test_toCharacter() {
        assertNull(toCharacter(null));
        assertNull(toCharacter(""));
        assertEquals('1', toCharacter('1'));
        assertEquals('1', toCharacter("123"));
    }

    @Test
    public void test_toShort() {
        assertNull(toShort(null));
        assertNull(toShort(""));
        assertEquals((short) 1, toShort((short) 1));
        assertEquals((short) 1, toShort(1));
        assertEquals((short) 1, toShort("1"));
    }

    @Test
    public void test_toInteger() {
        assertNull(toInteger(null));
        assertNull(toInteger(""));
        assertEquals(1, toInteger(1));
        assertEquals(1, toInteger(1L));
        assertEquals(1, toInteger("1"));
    }

    @Test
    public void test_toLong() {
        assertNull(toLong(null));
        assertNull(toLong(""));
        assertEquals(1L, toLong(1L));
        assertEquals(1L, toLong(1));
        assertEquals(1L, toLong("1"));
    }

    @Test
    public void test_toFloat() {
        assertNull(toFloat(null));
        assertNull(toFloat(""));
        assertEquals(1.0f, toFloat(1.0f));
        assertEquals(1.0f, toFloat(1));
        assertEquals(1.0f, toFloat("1"));
    }

    @Test
    public void test_toDouble() {
        assertNull(toDouble(null));
        assertNull(toDouble(""));
        assertEquals(1.0, toDouble(1.0));
        assertEquals(1.0, toDouble(1));
        assertEquals(1.0, toDouble("1"));
    }

    @Test
    public void test_field() throws Exception {
        assertEquals("null type", assertThrows(ValidationException.class, () -> field(null, null)).getMessage());
        assertEquals("blank name", assertThrows(ValidationException.class, () -> field(type, null)).getMessage());

        assertEquals(CA.class.getField("fa0"), field(CB.class, "fa0"));
        assertEquals(CA.class.getDeclaredField("fa1"), field(CB.class, "fa1"));
    }

    @Test
    public void test_method() throws Exception {
        assertEquals("null type", assertThrows(ValidationException.class, () -> method(null, null)).getMessage());
        assertEquals("blank name", assertThrows(ValidationException.class, () -> method(type, null)).getMessage());

        assertEquals(CA.class.getMethod("ma0"), method(CB.class, "ma0"));
        assertEquals(CA.class.getMethod("ma0"), method(CB.class, "ma0", (Class<?>[]) null));
        assertEquals(CA.class.getDeclaredMethod("ma1", Object.class), method(CB.class, "ma1", Object.class));
    }

    @Test
    public void test_object() {
        assertEquals("null type", assertThrows(ValidationException.class, () -> object(null, null, null)).getMessage());
        assertThrows(CoreException.class, () -> object(CA.class, new Class<?>[]{Object.class}, new Object[]{object}));

        assertTrue(object(CA.class, new Class<?>[0], new Object[0]) instanceof CA);
        assertTrue(object(CA.class, null, null) instanceof CA);
        assertTrue(object(CB.class, new Class<?>[]{Object.class}, new Object[]{object}) instanceof CB);
    }

    @Test
    public void test_instance() {
        assertEquals("null type", assertThrows(ValidationException.class, () -> instance(null)).getMessage());
        assertEquals("No enum constant demitasse.core.CoreToolsTest.EA.Instance", assertThrows(IllegalArgumentException.class, () -> instance(EA.class)).getMessage());

        InvocationTargetException e = (InvocationTargetException) assertThrows(CoreException.class, () -> instance(CE.class)).getCause();
        assertTrue(e.getCause() instanceof UnsupportedOperationException);

        assertEquals(EB.Instance, instance(EB.class));
        assertEquals(CA.INSTANCE, instance(CA.class));
        assertEquals(CB.cb, instance(CB.class));

        CC cc = instance(CC.class);
        assertNotNull(cc);
        assertNotEquals(CC.cc, cc);

        CD cd = instance(CD.class);
        assertNotNull(cd);
        assertNotEquals(CD.cd, cd);
    }

    @Test
    public void test_inspect() {
        assertEquals("null type", assertThrows(ValidationException.class, () -> inspect(null, null)).getMessage());
        assertEquals("null function", assertThrows(ValidationException.class, () -> inspect(type, null)).getMessage());

        assertNull(inspect(Object.class, o -> null));

        inspect(IA.class, o -> {
            assertEquals(IA.class, o);
            return new DefaultInspectionResult<>(false, null);
        });

        List<Class<?>> list_ca = new ArrayList<>(2);
        assertNull(inspect(CA.class, o -> {
            list_ca.add(o);
            return null;
        }));
        assertEquals(List.of(CA.class, IA.class), list_ca);

        List<Class<?>> list_cb = new ArrayList<>(3);
        assertNull(inspect(CB.class, o -> {
            list_cb.add(o);
            return new DefaultInspectionResult<>(null);
        }));
        assertEquals(List.of(CB.class, CA.class, IA.class), list_cb);

        assertEquals(CA.class, inspect(CB.class, o -> o == CA.class ? new DefaultInspectionResult<>(o) : new DefaultInspectionResult<>(false, null)));
    }

    //
    private interface IA {
    }

    //
    private enum EA {}

    //
    private enum EB {
        Instance
    }

    //
    private static class CA implements IA {
        private static final CA INSTANCE = new CA();
        public Object fa0;
        private Object fa1;

        public void ma0() {
        }

        private void ma1(Object object) {
        }
    }

    //
    private static class CB extends CA implements IA {
        private static final CB cb = new CB(null);

        private CB(Object object) {
        }

        private static CB instance() {
            return cb;
        }
    }

    //
    private static class CC {
        private static final CC cc = new CC();

        private CC instance() {
            return cc;
        }
    }

    //
    private static class CD {
        private static final CD cd = new CD();
        private final CD INSTANCE = cd;
    }

    //
    private static class CE {
        private static CE instance() {
            throw new UnsupportedOperationException();
        }
    }
}
