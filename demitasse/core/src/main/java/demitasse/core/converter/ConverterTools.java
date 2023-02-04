package demitasse.core.converter;

/**
 * ConverterTools
 */
public final class ConverterTools {
    private static final Noop<?> NOOP = new Noop<>(Object.class);

    /**
     * @return noop converter
     */
    public static <T> Converter<T, T> noopConverter() {
        return noopConverter(null);
    }

    /**
     * @param type type
     * @return noop converter
     */
    @SuppressWarnings("unchecked")
    public static <T> Converter<T, T> noopConverter(Class<T> type) {
        return (Converter<T, T>) ((type == null || type == Object.class) ? NOOP : new Noop<>(type));
    }

    //
    private record Noop<T>(Class<T> type) implements Converter<T, T> {

        @Override
        public T convert(T object) {
            return object;
        }

        @Override
        public Class<T> inputType() {
            return type();
        }

        @Override
        public Class<T> outputType() {
            return type();
        }

        @Override
        public Converter<T, T> reverse() {
            return this;
        }
    }

    //
    private ConverterTools() {
    }
}
