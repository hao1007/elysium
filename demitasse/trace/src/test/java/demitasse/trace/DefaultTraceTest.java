package demitasse.trace;

import demitasse.sys.Sys;
import demitasse.sys.SysFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.System.lineSeparator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link DefaultTrace}
 */
@ExtendWith(MockitoExtension.class)
public final class DefaultTraceTest {
    @Mock
    private TraceVisitor visitor;
    private SysFixture sys;
    private String id;
    private String description;
    private long create_time;
    private long begin_time;
    private long end_time;
    private String child_id;
    private long child_create_time;
    private String append_id;
    private long append_create_time;
    private DefaultTrace trace;

    @BeforeEach
    public void beforeEach() {
        sys = (SysFixture) Sys.sys();
        id = "id";
        description = "description";
        create_time = 1;
        begin_time = 2;
        end_time = 3;
        child_id = "child";
        child_create_time = 11;
        append_id = "append";
        append_create_time = 21;

        when(sys.mock().millis()).thenReturn(create_time);
        trace = new DefaultTrace();
    }

    @AfterEach
    public void afterEach() {
        reset(sys.mock());
    }

    @Test
    public void test_create() {
        assertEquals("time=" + create_time, trace.toString());

        trace.invite(visitor);
        verify(visitor).begin(null);
        verify(visitor).trace(create_time, 0, null);
        verify(visitor).end();
    }

    @Test
    public void test() {
        when(sys.mock().millis()).thenReturn(begin_time, end_time);

        assertEquals(trace, trace.id(id));
        trace.description(description);
        trace.begin();
        trace.end();
        assertEquals(String.format("%s time=%s duration=%s description=%s", id, begin_time, (end_time - begin_time), description), trace.toString());

        trace.invite(visitor);
        verify(visitor).begin(id);
        verify(visitor).trace(begin_time, end_time, description);
        verify(visitor).end();
    }

    @Test
    public void test_child() {
        when(sys.mock().millis()).thenReturn(child_create_time);

        Trace child = trace.child();
        assertEquals(child, child.id(child_id));
        String s = "time=" + create_time
                + lineSeparator() + " " + child_id + " time=" + child_create_time;
        assertEquals(s, trace.toString());

        trace.invite(visitor);
        verify(visitor).begin(null);
        verify(visitor).trace(create_time, 0, null);
        verify(visitor).begin(child_id);
        verify(visitor).trace(child_create_time, 0, null);
        verify(visitor, times(2)).end();
    }

    @Test
    public void test_append_null() {
        trace.append((Trace[]) null);
        assertEquals("time=" + create_time, trace.toString());

        trace.invite(visitor);
        verify(visitor).begin(null);
        verify(visitor).trace(create_time, 0, null);
        verify(visitor).end();
    }

    @Test
    public void test_append() {
        when(sys.mock().millis()).thenReturn(append_create_time);
        Trace append = new DefaultTrace().id(append_id);
        trace.append(null, append);

        String s = "time=" + create_time
                + lineSeparator() + " " + append_id + " time=" + append_create_time;
        assertEquals(s, trace.toString());

        trace.invite(visitor);
        verify(visitor).begin(null);
        verify(visitor).trace(create_time, 0, null);
        verify(visitor).begin(append_id);
        verify(visitor).trace(append_create_time, 0, null);
        verify(visitor, times(2)).end();
    }
}
