package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * BooleanToStringConverter
 */
public enum BooleanToStringConverter implements Converter<Boolean, String> {
    Instance;

    @Override
    public String convert(Boolean object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Boolean> inputType() {
        return Boolean.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Boolean> reverse() {
        return StringToBooleanConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
