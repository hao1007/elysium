package demitasse.prop;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link AbstractProps}
 */
@ExtendWith(MockitoExtension.class)
public final class AbstractPropsTest {
    @Mock
    private PropsVisitor visitor;
    @Mock
    private Props parent;
    private String id;
    private String key;
    private String value;
    private CA ca;

    @BeforeEach
    public void beforeEach() {
        id = "id";
        key = "key";
        value = "value";

        ca = new CA(id, parent);
    }

    @Test
    public void test_id() {
        assertEquals(id, ca.id());
        assertEquals("", new CA(null, null).id());
    }

    @Test
    public void test_has_null() {
        assertFalse(ca.has(null));
    }

    @Test
    public void test_has_true() {
        when(ca.mock.internalHas(key)).thenReturn(true);
        assertTrue(ca.has(key));
    }

    @Test
    public void test_has_parent_true() {
        when(parent.has(key)).thenReturn(true);
        when(ca.mock.internalHas(key)).thenReturn(false);
        assertTrue(ca.has(key));
    }

    @Test
    public void test_has_false() {
        when(parent.has(key)).thenReturn(false);
        when(ca.mock.internalHas(key)).thenReturn(false);
        assertFalse(ca.has(key));
    }

    @Test
    public void test_prop_exception() {
        assertEquals("null key", assertThrows(ValidationException.class, () -> ca.prop(null)).getMessage());

        when(parent.prop(key)).thenThrow(new PropNotFoundException(key));
        when(ca.mock.internalProp(key)).thenThrow(new PropNotFoundException());
        assertEquals(key, assertThrows(PropNotFoundException.class, () -> ca.prop(key)).getMessage());
    }

    @Test
    public void test_prop() {
        when(ca.mock.internalProp(key)).thenReturn(value);
        assertEquals(value, ca.prop(key));
    }

    @Test
    public void test_prop_parent() {
        when(parent.prop(key)).thenReturn(value);
        when(ca.mock.internalProp(key)).thenThrow(new PropNotFoundException());
        assertEquals(value, ca.prop(key));
    }

    @Test
    public void test_keys() {
        when(parent.keys()).thenReturn(Set.of());
        when(ca.mock.internalKeys()).thenReturn(Set.of());
        assertTrue(ca.keys().isEmpty());
    }

    @Test
    public void test_invite_null() {
        assertEquals("null visitor", assertThrows(ValidationException.class, () -> ca.invite(null)).getMessage());
    }

    @Test
    public void test_invite_empty() {
        ca.invite(visitor);
        verify(visitor).begin(id);
        verify(visitor, never()).prop(anyString(), anyString());
        verify(visitor).end();

        assertEquals("id", ca.toString());
    }

    @Test
    public void test_invite() {
        ca.prop(key, value);
        ca.invite(visitor);
        verify(visitor).begin(id);
        verify(visitor).prop(key, value);
        verify(visitor).end();

        String s = id + lineSeparator() + format("%s=%s [%s]", key, value, id);
        assertEquals(s, ca.toString());
    }

    //
    private static class CA extends AbstractProps {
        private final AbstractProps mock;
        private String key;
        private String value;

        private CA(String id, Props parent) {
            super(id, parent);
            mock = mock(AbstractProps.class);
        }

        private void prop(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        protected boolean internalHas(String key) {
            return mock.internalHas(key);
        }

        @Override
        protected String internalProp(String key) throws PropNotFoundException {
            return mock.internalProp(key);
        }

        @Override
        protected Set<String> internalKeys() {
            return mock.internalKeys();
        }

        @Override
        protected void internalInvite(PropsVisitor visitor) {
            if (key != null && value != null) visitor.prop(key, value);
        }
    }
}
