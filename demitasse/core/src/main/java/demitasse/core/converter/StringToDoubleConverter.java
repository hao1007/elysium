package demitasse.core.converter;

import static demitasse.core.CoreTools.toDouble;

/**
 * StringToDoubleConverter
 */
public enum StringToDoubleConverter implements Converter<String, Double> {
    Instance;

    @Override
    public Double convert(String object) {
        return toDouble(object);
    }

    @Override
    public Class<String> inputType() {
        return String.class;
    }

    @Override
    public Class<Double> outputType() {
        return Double.class;
    }

    @Override
    public Converter<Double, String> reverse() {
        return DoubleToStringConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
