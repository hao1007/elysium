package demitasse.core.converter;

import demitasse.core.CoreTools;

/**
 * DoubleToStringConverter
 */
public enum DoubleToStringConverter implements Converter<Double, String> {
    Instance;

    @Override
    public String convert(Double object) {
        return CoreTools.toString(object);
    }

    @Override
    public Class<Double> inputType() {
        return Double.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Double> reverse() {
        return StringToDoubleConverter.Instance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
