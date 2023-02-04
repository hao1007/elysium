package demitasse.prop;

import java.util.Objects;
import java.util.Set;

import static demitasse.core.CoreConstants.EMPTY;
import static demitasse.core.CoreTools.validate;

/**
 * EmptyProps
 */
public enum EmptyProps implements Props {
    Instance;

    @Override
    public String id() {
        return EMPTY;
    }

    @Override
    public boolean has(String key) {
        return false;
    }

    @Override
    public String prop(String key) throws PropNotFoundException {
        validate(key, Objects::nonNull, "null key");
        throw new PropNotFoundException(key);
    }

    @Override
    public Set<String> keys() {
        return Set.of();
    }

    @Override
    public void invite(PropsVisitor visitor) {
        validate(visitor, Objects::nonNull, "null visitor");
    }
}
