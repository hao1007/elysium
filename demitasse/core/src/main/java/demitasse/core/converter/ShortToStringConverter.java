package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * ShortToStringConverter
 */
public enum ShortToStringConverter implements Converter<Short, String> {
    Instance;

    @Override
    public String convert(Short object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Short> inputType() {
        return Short.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Short> reverse() {
        return StringToShortConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
