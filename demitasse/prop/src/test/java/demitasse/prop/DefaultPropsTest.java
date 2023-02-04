package demitasse.prop;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

/**
 * {@link DefaultProps}
 */
@ExtendWith(MockitoExtension.class)
public final class DefaultPropsTest {
    @Mock
    private PropsVisitor visitor;
    private String key;
    private String value;
    private DefaultProps props;

    @BeforeEach
    public void beforeEach() {
        key = "key";
        value = "value";
        props = new DefaultProps(null, null, Map.of(key, value));
    }

    @Test
    public void test_exception() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(key, null);
        map.put(null, value);
        assertEquals("null key", assertThrows(ValidationException.class, () -> new DefaultProps(null, null, map)).getMessage());
    }

    @Test
    public void test_empty() {
        assertTrue(new DefaultProps(null, null, null).keys().isEmpty());
    }

    @Test
    public void test_has() {
        assertTrue(props.has(key));
    }

    @Test
    public void test_prop() {
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_props_exception() {
        assertEquals(value, assertThrows(PropNotFoundException.class, () -> props.prop(value)).getMessage());
    }

    @Test
    public void test_props_null() {
        Map<String, String> map = new HashMap<>();
        map.put(key, null);
        assertNull(new DefaultProps(null, null, map).prop(key));
    }

    @Test
    public void test_invite() {
        props.invite(visitor);

        verify(visitor).begin("");
        verify(visitor).prop(key, value);
        verify(visitor).end();

    }
}
