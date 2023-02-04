package demitasse.prop;

/**
 * PropsVisitor
 */
public interface PropsVisitor {
    /**
     * @param id id
     */
    void begin(String id);

    /**
     * @param key   key
     * @param value value
     */
    void prop(String key, String value);

    /**
     *
     */
    void end();
}
