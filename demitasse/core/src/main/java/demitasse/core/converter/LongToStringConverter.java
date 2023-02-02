package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * LongToStringConverter
 */
public enum LongToStringConverter implements Converter<Long, String> {
    Instance;

    @Override
    public String convert(Long object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Long> inputType() {
        return Long.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Long> reverse() {
        return StringToLongConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
