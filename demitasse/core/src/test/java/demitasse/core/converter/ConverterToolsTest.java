package demitasse.core.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static demitasse.core.converter.ConverterTools.noopConverter;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link ConverterTools}
 */
public final class ConverterToolsTest {
    private Object object;
    private String string;

    @BeforeEach
    public void beforeEach() {
        object = new Object();
        string = "string";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void test_noopConverter() {
        Converter converter = noopConverter();
        assertEquals(object, converter.convert(object));
        assertEquals(Object.class, converter.inputType());
        assertEquals(Object.class, converter.outputType());
        assertEquals(converter, converter.reverse());
    }

    @Test
    public void test_noopConverter_object() {
        Converter<Object, Object> converter = noopConverter(Object.class);
        assertEquals(object, converter.convert(object));
        assertEquals(Object.class, converter.inputType());
        assertEquals(Object.class, converter.outputType());
        assertEquals(converter, converter.reverse());
    }

    @Test
    public void test_noopConverter_string() {
        Converter<String, String> converter = noopConverter(String.class);
        assertEquals(string, converter.convert(string));
        assertEquals(String.class, converter.inputType());
        assertEquals(String.class, converter.outputType());
        assertEquals(converter, converter.reverse());
    }
}
