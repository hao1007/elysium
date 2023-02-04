package demitasse.prop;

/**
 * AbstractPropsBuilder
 */
public abstract class AbstractPropsBuilder<T extends AbstractPropsBuilder<T>> implements PropsBuilder {
    private String id;
    private PropsBuilder parent;
    private Props props;

    protected AbstractPropsBuilder() {
        parent = EmptyPropsBuilder.Instance;
    }

    /**
     * @param id id
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T id(String id) {
        this.id = id;
        return (T) this;
    }

    /**
     * @param parent parent
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T parent(Props parent) {
        this.parent = parent == null ? EmptyPropsBuilder.Instance : wrap(parent);
        return (T) this;
    }

    /**
     * @param parent parent
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public T parent(PropsBuilder parent) {
        this.parent = parent == null ? EmptyPropsBuilder.Instance : parent;
        return (T) this;
    }

    /**
     *
     */
    public void reset() {
        id = null;
        parent = EmptyPropsBuilder.Instance;
        props = null;
    }

    @Override
    public Props build() {
        if (props == null) props = internalBuild(id, parent.build());
        return props;
    }

    protected abstract Props internalBuild(String id, Props parent);

    protected PropsBuilder wrap(Props props) {
        return new Wrapper(props);
    }

    //
    private record Wrapper(Props props) implements PropsBuilder {
        @Override
        public Props build() {
            return props();
        }
    }
}
