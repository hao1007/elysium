package demitasse.core.converter;

import static demitasse.core.CoreTools.toByte;

/**
 * StringToByteConverter
 */
public enum StringToByteConverter implements Converter<String, Byte> {
    Instance;

    @Override
    public Byte convert(String object) {
        return toByte(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Byte> outputType() {
        return Byte.class;
    }

    @Override
    public Converter<Byte, String> reverse() {
        return ByteToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
