package demitasse.prop;

/**
 * EmptyPropsBuilder
 */
public enum EmptyPropsBuilder implements PropsBuilder {
    Instance;

    @Override
    public Props build() {
        return EmptyProps.Instance;
    }
}
