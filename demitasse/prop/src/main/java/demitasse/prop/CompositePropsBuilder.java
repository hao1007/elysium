package demitasse.prop;

import java.util.LinkedList;
import java.util.List;

/**
 * CompositePropsBuilder
 */
public final class CompositePropsBuilder extends AbstractPropsBuilder<CompositePropsBuilder> {
    private final List<PropsBuilder> list;

    /**
     *
     */
    public CompositePropsBuilder() {
        list = new LinkedList<>();
    }

    /**
     * @param props props
     * @return builder
     */
    public CompositePropsBuilder append(Props props) {
        if (props != null) list.add(wrap(props));
        return this;
    }

    /**
     * @param builder builder
     * @return builder
     */
    public CompositePropsBuilder append(PropsBuilder builder) {
        if (builder != null) list.add(builder);
        return this;
    }

    /**
     * @param props props
     * @return builder
     */
    public CompositePropsBuilder prepend(Props props) {
        if (props != null) list.add(0, wrap(props));
        return this;
    }

    /**
     * @param builder builder
     * @return builder
     */
    public CompositePropsBuilder prepend(PropsBuilder builder) {
        if (builder != null) list.add(0, builder);
        return this;
    }

    @Override
    public void reset() {
        list.clear();
        super.reset();
    }

    @Override
    protected Props internalBuild(String id, Props parent) {
        Props[] array = list.stream().map(PropsBuilder::build).toArray(Props[]::new);
        return new CompositeProps(id, parent, array);
    }
}
