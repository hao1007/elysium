package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * FloatToStringConverter
 */
public enum FloatToStringConverter implements Converter<Float, String> {
    Instance;

    @Override
    public String convert(Float object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Float> inputType() {
        return Float.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Float> reverse() {
        return StringToFloatConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
