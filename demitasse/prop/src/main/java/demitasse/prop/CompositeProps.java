package demitasse.prop;

import demitasse.core.CoreTools;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * CompositeProps
 */
public final class CompositeProps extends AbstractProps {
    private final Props[] array;

    /**
     * @param id     id
     * @param parent parent
     * @param array  array
     */
    public CompositeProps(String id, Props parent, Props... array) {
        super(id, parent);
        if (array == null || array.length == 0) {
            this.array = new Props[0];
        } else {
            this.array = new Props[array.length];
            for (int i = 0; i < array.length; i++) {
                this.array[i] = CoreTools.validate(array[i], Objects::nonNull, "null props");
            }
        }
    }

    @Override
    protected boolean internalHas(String key) {
        for (Props props : array) {
            if (props.has(key)) return true;
        }
        return false;
    }

    @Override
    protected String internalProp(String key) throws PropNotFoundException {
        for (Props props : array) {
            try {
                return props.prop(key);
            } catch (PropNotFoundException e) {
                // ignore
            }
        }
        throw new PropNotFoundException(key);
    }

    @Override
    protected Set<String> internalKeys() {
        Set<String> set = new HashSet<>();
        for (Props props : array) set.addAll(props.keys());
        return set;
    }

    @Override
    protected void internalInvite(PropsVisitor visitor) {
        for (Props props : array) props.invite(visitor);
    }
}
