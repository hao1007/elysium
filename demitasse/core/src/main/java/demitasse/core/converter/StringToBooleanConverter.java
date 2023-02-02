package demitasse.core.converter;

import static demitasse.core.CoreTools.toBoolean;

/**
 * StringToBooleanConverter
 */
public enum StringToBooleanConverter implements Converter<String, Boolean> {
    Instance;

    @Override
    public Boolean convert(String object) {
        return toBoolean(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Boolean> outputType() {
        return Boolean.class;
    }

    @Override
    public Converter<Boolean, String> reverse() {
        return BooleanToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
