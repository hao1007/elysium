package demitasse.prop;

import demitasse.core.CoreConstants;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static demitasse.core.CoreConstants.EOL;
import static demitasse.core.CoreTools.isBlank;
import static demitasse.core.CoreTools.validate;
import static java.lang.String.format;

/**
 * AbstractProps
 */
public abstract class AbstractProps implements Props {
    private final String id;
    private final Props parent;

    protected AbstractProps(String id, Props parent) {
        this.id = isBlank(id) ? CoreConstants.EMPTY : id;
        this.parent = parent == null ? EmptyProps.Instance : parent;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public boolean has(String key) {
        if (key == null) return false;
        return internalHas(key) || parent.has(key);
    }

    @Override
    public String prop(String key) throws PropNotFoundException {
        validate(key, Objects::nonNull, "null key");
        try {
            return internalProp(key);
        } catch (PropNotFoundException e) {
            return parent.prop(key);
        }
    }

    @Override
    public Set<String> keys() {
        Set<String> set = new TreeSet<>();
        set.addAll(internalKeys());
        set.addAll(parent.keys());
        return set;
    }

    @Override
    public void invite(PropsVisitor visitor) {
        validate(visitor, Objects::nonNull, "null visitor");
        visitor.begin(id);
        internalInvite(visitor);
        parent.invite(visitor);
        visitor.end();
    }

    protected abstract boolean internalHas(String key);

    protected abstract String internalProp(String key) throws PropNotFoundException;

    protected abstract Set<String> internalKeys();

    protected abstract void internalInvite(PropsVisitor visitor);

    @Override
    public String toString() {
        ToString to_string = new ToString(id);
        invite(to_string);
        return to_string.toString();
    }

    //
    private static final class ToString implements PropsVisitor {
        private final String id;
        private final Deque<String> ids;
        private final Map<String, String[]> map;

        private ToString(String id) {
            this.id = id;
            ids = new LinkedList<>();
            map = new TreeMap<>();
        }

        @Override
        public void begin(String id) {
            ids.push(id);
        }

        @Override
        public void prop(String key, String value) {
            map.computeIfAbsent(key, o -> new String[]{value, ids.peek()});
        }

        @Override
        public void end() {
            ids.pop();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(id);
            map.forEach((k, v) -> builder.append(EOL).append(format("%s=%s [%s]", k, v[0], v[1])));
            return builder.toString();
        }
    }
}
