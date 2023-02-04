package demitasse.prop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static demitasse.core.CoreTools.isEmpty;
import static demitasse.core.CoreTools.validate;

/**
 * DefaultPropsBuilder
 */
public final class DefaultPropsBuilder extends AbstractPropsBuilder<DefaultPropsBuilder> {
    private final Map<String, String> map;

    /**
     *
     */
    public DefaultPropsBuilder() {
        map = new HashMap<>();
    }

    /**
     * @param key   key
     * @param value value
     * @return builder
     */
    public DefaultPropsBuilder prop(Object key, Object value) {
        put(map, key, value);
        return this;
    }

    /**
     * @param map map
     * @return builder
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DefaultPropsBuilder props(Map map) {
        if (!isEmpty(map)) {
            Map<String, String> m = new HashMap<>(map.size());
            map.forEach((k, v) -> put(m, k, v));
            this.map.putAll(m);
        }
        return this;
    }

    @Override
    public void reset() {
        map.clear();
        super.reset();
    }

    @Override
    protected Props internalBuild(String id, Props parent) {
        return new DefaultProps(id, parent, map);
    }

    private static void put(Map<String, String> map, Object key, Object value) {
        String k = validate(key == null ? null : String.valueOf(key), Objects::nonNull, "null key");
        String v = value == null ? null : String.valueOf(value);
        map.put(k, v);
    }
}
