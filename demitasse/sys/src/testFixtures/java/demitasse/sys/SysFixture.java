package demitasse.sys;

import org.mockito.Mockito;

/**
 * SysFixture
 */
public final class SysFixture extends Sys {
    private final Sys mock;

    private SysFixture() {
        mock = Mockito.mock(Sys.class);
    }

    /**
     * @return mock
     */
    public Sys mock() {
        return mock;
    }

    @Override
    public long millis() {
        return mock.millis();
    }
}
