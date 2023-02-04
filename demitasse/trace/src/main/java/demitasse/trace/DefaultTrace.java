package demitasse.trace;

import demitasse.sys.Sys;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static demitasse.core.CoreConstants.EMPTY;
import static demitasse.core.CoreConstants.EOL;
import static demitasse.core.CoreConstants.SPACE;
import static demitasse.core.CoreTools.isBlank;
import static demitasse.core.CoreTools.isEmpty;
import static demitasse.core.CoreTools.validate;
import static demitasse.sys.Sys.sys;

/**
 * DefaultTrace
 */
public final class DefaultTrace implements Trace {
    private static final Sys SYS = sys();

    private final long create_time;
    private final Collection<Trace> child_traces;
    private String id;
    private String description;
    private long begin_time;
    private long end_time;

    /**
     *
     */
    public DefaultTrace() {
        create_time = SYS.millis();
        child_traces = new CopyOnWriteArrayList<>();
    }

    @Override
    public Trace id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public void description(String description) {
        this.description = description;
    }

    @Override
    public void begin() {
        begin_time = SYS.millis();
    }

    @Override
    public void end() {
        end_time = SYS.millis();
    }

    @Override
    public Trace child() {
        Trace child = new DefaultTrace();
        child_traces.add(child);
        return child;
    }

    @Override
    public void append(Trace... traces) {
        if (traces == null) return;
        for (Trace trace : traces) {
            if (trace != null) child_traces.add(trace);
        }
    }

    @Override
    public void invite(TraceVisitor visitor) {
        validate(visitor, Objects::nonNull, "null visitor");
        visitor.begin(id);
        visitor.trace(begin_time == 0 ? create_time : begin_time, end_time, description);
        child_traces.forEach(o -> o.invite(visitor));
        visitor.end();
    }

    @Override
    public String toString() {
        ToString to_string = new ToString();
        invite(to_string);
        return to_string.toString();
    }

    //
    private static final class ToString implements TraceVisitor {
        private final Deque<String> ids;
        private final StringBuilder builder;

        private ToString() {
            ids = new LinkedList<>();
            builder = new StringBuilder();
        }

        @Override
        public void begin(String id) {
            ids.push(isBlank(id) ? EMPTY : id);
        }

        @Override
        public void trace(long begin, long end, String description) {
            int indent = ids.size() - 1;
            if (indent > 0) builder.append(EOL).append(SPACE.repeat(indent));
            String id = ids.peek();
            if (!isEmpty(id)) builder.append(id).append(SPACE);
            builder.append("time=").append(begin);
            if (end > 0) builder.append(" duration=").append(end - begin);
            if (!isBlank(description)) builder.append(" description=").append(description);
        }

        @Override
        public void end() {
            ids.pop();
        }

        @Override
        public String toString() {
            return builder.toString();
        }
    }
}
