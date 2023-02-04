package demitasse.trace;

/**
 * Trace
 */
public interface Trace {
    /**
     * @param id id
     * @return trace
     */
    Trace id(String id);

    /**
     * @param description description
     */
    void description(String description);

    /**
     *
     */
    void begin();

    /**
     *
     */
    void end();

    /**
     * @return child
     */
    Trace child();

    /**
     * @param traces traces
     */
    void append(Trace... traces);

    /**
     * @param visitor visitor
     */
    void invite(TraceVisitor visitor);
}
