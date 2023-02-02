package demitasse.core.util;

import demitasse.core.CoreTools;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static demitasse.core.CoreConstants.HYPHEN;
import static demitasse.core.CoreTools.validate;
import static java.lang.Thread.NORM_PRIORITY;
import static java.util.function.Predicate.not;

/**
 * DefaultThreadFactory
 */
public final class DefaultThreadFactory implements ThreadFactory {
    private final String name;
    private final boolean daemon;
    private final int priority;
    @ToString(ignore = true)
    private final AtomicLong count;

    /**
     * @param name name
     */
    public DefaultThreadFactory(String name) {
        this(name, true, NORM_PRIORITY);
    }

    /**
     * @param name     name
     * @param daemon   daemon
     * @param priority priority
     */
    public DefaultThreadFactory(String name, boolean daemon, int priority) {
        this.name = validate(name, not(CoreTools::isBlank), "blank name");
        this.daemon = daemon;
        this.priority = priority;
        count = new AtomicLong();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, name + HYPHEN + count.getAndIncrement());
        if (thread.isDaemon() != daemon) thread.setDaemon(daemon);
        if (thread.getPriority() != priority) thread.setPriority(priority);
        return thread;
    }

    @Override
    public String toString() {
        return ToStringTools.toString(this);
    }
}
