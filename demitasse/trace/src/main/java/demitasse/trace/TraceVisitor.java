package demitasse.trace;

/**
 * TraceVisitor
 */
public interface TraceVisitor {
    /***
     *
     * @param id id
     */
    void begin(String id);

    /**
     * @param begin       begin
     * @param end         end
     * @param description description
     */
    void trace(long begin, long end, String description);

    /**
     *
     */
    void end();
}
