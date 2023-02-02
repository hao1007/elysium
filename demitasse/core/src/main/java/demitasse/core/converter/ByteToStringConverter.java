package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * ByteToStringConverter
 */
public enum ByteToStringConverter implements Converter<Byte, String> {
    Instance;

    @Override
    public String convert(Byte object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Byte> inputType() {
        return Byte.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Byte> reverse() {
        return StringToByteConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
