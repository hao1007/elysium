package demitasse.core.converter;

import org.junit.jupiter.api.Test;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Converter}
 */
public final class ConverterTest {
    @Test
    public void test() {
        test(BooleanToStringConverter.Instance, Boolean.class, String.class, StringToBooleanConverter.Instance, "BooleanToStringConverter", TRUE, "true");
        test(ByteToStringConverter.Instance, Byte.class, String.class, StringToByteConverter.Instance, "ByteToStringConverter", (byte) 1, "1");
        test(CharacterToStringConverter.Instance, Character.class, String.class, StringToCharacterConverter.Instance, "CharacterToStringConverter", '1', "1");
        test(ShortToStringConverter.Instance, Short.class, String.class, StringToShortConverter.Instance, "ShortToStringConverter", (short) 1, "1");
        test(IntegerToStringConverter.Instance, Integer.class, String.class, StringToIntegerConverter.Instance, "IntegerToStringConverter", 1, "1");
        test(LongToStringConverter.Instance, Long.class, String.class, StringToLongConverter.Instance, "LongToStringConverter", 1L, "1");
        test(FloatToStringConverter.Instance, Float.class, String.class, StringToFloatConverter.Instance, "FloatToStringConverter", 1.0f, "1.0");
        test(DoubleToStringConverter.Instance, Double.class, String.class, StringToDoubleConverter.Instance, "DoubleToStringConverter", 1.0, "1.0");

        test(StringToBooleanConverter.Instance, String.class, Boolean.class, BooleanToStringConverter.Instance, "StringToBooleanConverter", "true", TRUE);
        test(StringToByteConverter.Instance, String.class, Byte.class, ByteToStringConverter.Instance, "StringToByteConverter", "1", (byte) 1);
        test(StringToCharacterConverter.Instance, String.class, Character.class, CharacterToStringConverter.Instance, "StringToCharacterConverter", "123", '1');
        test(StringToShortConverter.Instance, String.class, Short.class, ShortToStringConverter.Instance, "StringToShortConverter", "1", (short) 1);
        test(StringToIntegerConverter.Instance, String.class, Integer.class, IntegerToStringConverter.Instance, "StringToIntegerConverter", "1", 1);
        test(StringToLongConverter.Instance, String.class, Long.class, LongToStringConverter.Instance, "StringToLongConverter", "1", 1L);
        test(StringToFloatConverter.Instance, String.class, Float.class, FloatToStringConverter.Instance, "StringToFloatConverter", "1", 1.0f);
        test(StringToDoubleConverter.Instance, String.class, Double.class, DoubleToStringConverter.Instance, "StringToDoubleConverter", "1", 1.0);
    }

    private static <A, B> void test(Converter<A, B> converter, Class<A> input_type, Class<B> output_type, Converter<B, A> reverse_converter, String to_string, A input, B output) {
        assertEquals(input_type, converter.inputType());
        assertEquals(output_type, converter.outputType());
        assertEquals(reverse_converter, converter.reverse());
        assertEquals(to_string, converter.toString());
        assertEquals(output, converter.convert(input));
    }
}
