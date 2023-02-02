package demitasse.core.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link ToStringConverter}
 */
public final class ToStringConverterTest {
    private ToStringConverter converter;
    private Type type;
    private Object object;

    @BeforeEach
    public void beforeEach() {
        converter = ToStringConverter.Instance;
        type = Object.class;
        object = new Object();
    }

    @Test
    public void test_convert_null() {
        assertEquals("null", converter.convert(null));
    }

    @Test
    public void test_convert_type() {
        assertEquals(type.getTypeName(), converter.convert(type));
    }

    @Test
    public void test_convert_array() {
        Object[] array = {null, type, object, new Object[]{null, type, object}};
        String s = String.format("[null,%s,%s,[null,%s,%s]]", type.getTypeName(), object, type.getTypeName(), object);
        assertEquals(s, converter.convert(array));
    }

    @Test
    public void test_convert() {
        assertEquals(String.valueOf(object), converter.convert(object));
    }

    @Test
    public void test_inputType() {
        assertEquals(Object.class, converter.inputType());
    }

    @Test
    public void test_outputType() {
        assertEquals(String.class, converter.outputType());
    }

    @Test
    public void test_reverse() {
        assertThrows(UnsupportedOperationException.class, () -> converter.reverse());
    }

    @Test
    public void test_toString() {
        assertEquals("ToStringConverter", converter.toString());
    }
}
