package demitasse.trace;

import java.util.Objects;

import static demitasse.core.CoreTools.validate;

/**
 * NoopTrace
 */
public enum NoopTrace implements Trace {
    Instance;

    @Override
    public Trace id(String id) {
        return this;
    }

    @Override
    public void description(String description) {
        // noop
    }

    @Override
    public void begin() {
        // noop
    }

    @Override
    public void end() {
        // noop
    }

    @Override
    public Trace child() {
        return this;
    }

    @Override
    public void append(Trace... traces) {
        // noop
    }

    @Override
    public void invite(TraceVisitor visitor) {
        validate(visitor, Objects::nonNull, "null visitor");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
