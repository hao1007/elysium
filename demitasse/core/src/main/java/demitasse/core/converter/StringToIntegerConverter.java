package demitasse.core.converter;

import static demitasse.core.CoreTools.toInteger;

/**
 * StringToIntegerConverter
 */
public enum StringToIntegerConverter implements Converter<String, Integer> {
    Instance;

    @Override
    public Integer convert(String object) {
        return toInteger(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Integer> outputType() {
        return Integer.class;
    }

    @Override
    public Converter<Integer, String> reverse() {
        return IntegerToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
