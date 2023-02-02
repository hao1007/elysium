package demitasse.core.util;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.Thread.MAX_PRIORITY;
import static java.lang.Thread.NORM_PRIORITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link DefaultThreadFactory}
 */
@ExtendWith(MockitoExtension.class)
public final class DefaultThreadFactoryTest {
    @Mock
    private Runnable runnable;
    private String name;

    @BeforeEach
    public void beforeEach() {
        name = "name";
    }

    @Test
    public void test_exception() {
        assertEquals("blank name", assertThrows(ValidationException.class, () -> new DefaultThreadFactory(null)).getMessage());
    }

    @Test
    public void test_default() {
        DefaultThreadFactory factory = new DefaultThreadFactory(name);
        Thread thread = factory.newThread(runnable);
        assertEquals(name + "-0", thread.getName());
        assertTrue(thread.isDaemon());
        assertEquals(NORM_PRIORITY, thread.getPriority());
        assertEquals(DefaultThreadFactory.class.getName() + "{daemon=true,name=name,priority=5}", factory.toString());
    }

    @Test
    public void test() {
        DefaultThreadFactory factory = new DefaultThreadFactory(name, false, MAX_PRIORITY);
        Thread thread = factory.newThread(runnable);
        assertEquals(name + "-0", thread.getName());
        assertFalse(thread.isDaemon());
        assertEquals(MAX_PRIORITY, thread.getPriority());
        assertEquals(DefaultThreadFactory.class.getName() + "{daemon=false,name=name,priority=10}", factory.toString());
    }
}
