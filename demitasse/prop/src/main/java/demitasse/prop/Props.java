package demitasse.prop;

import java.util.Set;

/**
 * Props
 */
public interface Props {
    /**
     * @return id
     */
    String id();

    /**
     * @param key key
     * @return true if exists, false otherwise
     */
    boolean has(String key);

    /**
     * @param key key
     * @return prop
     */
    String prop(String key) throws PropNotFoundException;

    /**
     * @return keys
     */
    Set<String> keys();

    /**
     * @param visitor visitor
     */
    void invite(PropsVisitor visitor);
}
