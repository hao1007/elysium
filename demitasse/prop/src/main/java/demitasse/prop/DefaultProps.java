package demitasse.prop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static demitasse.core.CoreTools.isEmpty;
import static demitasse.core.CoreTools.validate;

/**
 * DefaultProps
 */
public final class DefaultProps extends AbstractProps {
    private final Map<String, String> map;

    /**
     * @param id     id
     * @param parent parent
     * @param map    map
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DefaultProps(String id, Props parent, Map map) {
        super(id, parent);
        if (isEmpty(map)) {
            this.map = Map.of();
        } else {
            this.map = new HashMap<>(map.size());
            map.forEach((k, v) -> {
                String key = validate(k == null ? null : String.valueOf(k), Objects::nonNull, "null key");
                String value = v == null ? null : String.valueOf(v);
                this.map.put(key, value);
            });
        }
    }

    @Override
    protected boolean internalHas(String key) {
        return map.containsKey(key);
    }

    @Override
    protected String internalProp(String key) throws PropNotFoundException {
        String value = map.get(key);
        if (value == null && !map.containsKey(key)) throw new PropNotFoundException(key);
        return value;
    }

    @Override
    protected Set<String> internalKeys() {
        return Set.copyOf(map.keySet());
    }

    @Override
    protected void internalInvite(PropsVisitor visitor) {
        map.forEach(visitor::prop);
    }
}
