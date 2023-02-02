package demitasse.core.converter;

import static demitasse.core.CoreTools.toShort;

/**
 * StringToShortConverter
 */
public enum StringToShortConverter implements Converter<String, Short> {
    Instance;

    @Override
    public Short convert(String object) {
        return toShort(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Short> outputType() {
        return Short.class;
    }

    @Override
    public Converter<Short, String> reverse() {
        return ShortToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
