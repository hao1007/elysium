package demitasse.core.converter;

import static demitasse.core.CoreTools.toFloat;

/**
 * StringToFloatConverter
 */
public enum StringToFloatConverter implements Converter<String, Float> {
    Instance;

    @Override
    public Float convert(String object) {
        return toFloat(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Float> outputType() {
        return Float.class;
    }

    @Override
    public Converter<Float, String> reverse() {
        return FloatToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
