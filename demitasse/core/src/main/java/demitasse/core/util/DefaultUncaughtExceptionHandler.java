package demitasse.core.util;

import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * DefaultUncaughtExceptionHandler
 */
public enum DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
    Instance;

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        PrintStream err = System.err;
        err.println("uncaught exception: " + thread.getName());
        exception.printStackTrace(err);
        err.flush();
        System.exit(1);
    }
}
