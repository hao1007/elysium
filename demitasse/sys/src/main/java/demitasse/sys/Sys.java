package demitasse.sys;

import static demitasse.core.CoreTools.load;
import static java.lang.System.currentTimeMillis;

/**
 * Sys
 */
public abstract class Sys {
    private static final Sys SYS;

    static {
        SYS = load("demitasse.sys", Sys.class, () -> DefaultSys.INSTANCE);
    }

    /**
     * @return sys
     */
    public static Sys sys() {
        return SYS;
    }

    /**
     * @return millis
     */
    public abstract long millis();

    //
    private static final class DefaultSys extends Sys {
        private static final DefaultSys INSTANCE = new DefaultSys();

        @Override
        public long millis() {
            return currentTimeMillis();
        }
    }
}
