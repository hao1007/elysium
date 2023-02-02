package demitasse.core.converter;

import static demitasse.core.CoreTools.toLong;

/**
 * StringToLongConverter
 */
public enum StringToLongConverter implements Converter<String, Long> {
    Instance;

    @Override
    public Long convert(String object) {
        return toLong(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Long> outputType() {
        return Long.class;
    }

    @Override
    public Converter<Long, String> reverse() {
        return LongToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
