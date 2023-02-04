package demitasse.prop;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link CompositeProps}
 */
@ExtendWith(MockitoExtension.class)
public final class CompositePropsTest {
    @Mock
    private PropsVisitor visitor;
    @Mock
    private Props mock_props0;
    @Mock
    private Props mock_props1;
    private String key;
    private String value;
    private CompositeProps props;

    @BeforeEach
    public void beforeEach() {
        key = "key";
        value = "value";
        props = new CompositeProps(null, null, mock_props0, mock_props1);
    }

    @Test
    public void test_exception() {
        assertEquals("null props", assertThrows(ValidationException.class, () -> new CompositeProps(null, null, (Props) null)).getMessage());
    }

    @Test
    public void test() {
        assertTrue(new CompositeProps(null, null, (Props[]) null).keys().isEmpty());
        assertTrue(new CompositeProps(null, null).keys().isEmpty());
    }

    @Test
    public void test_has_true() {
        when(mock_props0.has(key)).thenReturn(false);
        when(mock_props1.has(key)).thenReturn(true);
        assertTrue(props.has(key));
    }

    @Test
    public void test_has_false() {
        when(mock_props0.has(key)).thenReturn(false);
        when(mock_props1.has(key)).thenReturn(false);
        assertFalse(props.has(key));
    }

    @Test
    public void test_prop() {
        when(mock_props0.prop(key)).thenThrow(new PropNotFoundException(key));
        when(mock_props1.prop(key)).thenReturn(value);
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_prop_exception() {
        when(mock_props0.prop(key)).thenThrow(new PropNotFoundException());
        when(mock_props1.prop(key)).thenThrow(new PropNotFoundException());
        assertEquals(key, assertThrows(PropNotFoundException.class, () -> props.prop(key)).getMessage());
    }

    @Test
    public void test_keys() {
        when(mock_props0.keys()).thenReturn(Set.of(key));
        when(mock_props1.keys()).thenReturn(Set.of(value));
        assertEquals(Set.of(key, value), props.keys());
    }

    @Test
    public void test_invite() {
        props.invite(visitor);
        verify(mock_props0).invite(visitor);
        verify(mock_props1).invite(visitor);
    }
}
