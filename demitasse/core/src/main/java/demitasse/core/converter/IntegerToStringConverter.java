package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * IntegerToStringConverter
 */
public enum IntegerToStringConverter implements Converter<Integer, String> {
    Instance;

    @Override
    public String convert(Integer object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Integer> inputType() {
        return Integer.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Integer> reverse() {
        return StringToIntegerConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
