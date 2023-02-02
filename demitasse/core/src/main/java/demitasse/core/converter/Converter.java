package demitasse.core.converter;

/**
 * Converter
 */
public interface Converter<A, B> {
    B convert(A object);

    Class<A> inputType();

    Class<B> outputType();

    Converter<B, A> reverse();
}
