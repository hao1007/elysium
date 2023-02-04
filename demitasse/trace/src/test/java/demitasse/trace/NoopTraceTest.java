package demitasse.trace;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * {@link NoopTrace}
 */
@ExtendWith(MockitoExtension.class)
public final class NoopTraceTest {
    @Mock
    private TraceVisitor visitor;
    private NoopTrace trace;

    @BeforeEach
    public void beforeEach() {
        trace = NoopTrace.Instance;
    }

    @Test
    public void test_id() {
        assertEquals(trace, trace.id(null));
    }

    @Test
    public void test_description() {
        trace.description(null);
    }

    @Test
    public void test_begin() {
        trace.begin();
    }

    @Test
    public void test_end() {
        trace.end();
    }

    @Test
    public void test_child() {
        assertEquals(trace, trace.child());
    }

    @Test
    public void test_append() {
        trace.append();
    }

    @Test
    public void test_invite() {
        assertEquals("null visitor", assertThrows(ValidationException.class, () -> trace.invite(null)).getMessage());

        trace.invite(visitor);
        verify(visitor, never()).begin(anyString());
        verify(visitor, never()).trace(anyLong(), anyLong(), anyString());
        verify(visitor, never()).end();
    }

    @Test
    public void test_toString() {
        assertEquals("NoopTrace", trace.toString());
    }
}
