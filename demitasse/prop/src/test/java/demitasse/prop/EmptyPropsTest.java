package demitasse.prop;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * {@link EmptyProps}
 */
@ExtendWith(MockitoExtension.class)
public final class EmptyPropsTest {
    @Mock
    private PropsVisitor visitor;
    private EmptyProps props;
    private String key;

    @BeforeEach
    public void beforeEach() {
        props = EmptyProps.Instance;
        key = "key";
    }

    @Test
    public void test_id() {
        assertEquals("", props.id());
    }

    @Test
    public void test_has() {
        assertFalse(props.has(null));
    }

    @Test
    public void test_prop() {
        assertEquals("null key", assertThrows(ValidationException.class, () -> props.prop(null)).getMessage());
        assertEquals(key, assertThrows(PropNotFoundException.class, () -> props.prop(key)).getMessage());
    }

    @Test
    public void test_keys() {
        assertTrue(props.keys().isEmpty());
    }

    @Test
    public void test_invite() {
        assertEquals("null visitor", assertThrows(ValidationException.class, () -> props.invite(null)).getMessage());

        props.invite(visitor);
        verify(visitor, never()).begin(anyString());
        verify(visitor, never()).prop(anyString(), anyString());
        verify(visitor, never()).end();
    }
}
