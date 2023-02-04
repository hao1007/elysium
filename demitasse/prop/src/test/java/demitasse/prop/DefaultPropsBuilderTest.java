package demitasse.prop;

import demitasse.core.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link DefaultPropsBuilder}
 */
public final class DefaultPropsBuilderTest {
    private DefaultPropsBuilder builder;
    private String key;
    private String value;

    @BeforeEach
    public void beforeEach() {
        builder = new DefaultPropsBuilder();
        key = "key";
        value = "value";
    }

    @AfterEach
    public void afterEach() {
        builder.reset();
    }

    @Test
    public void test_prop() {
        assertEquals(builder, builder.prop(key, value));

        Props props = builder.build();
        assertEquals(Set.of(key), props.keys());
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_props_empty() {
        assertEquals(builder, builder.props(Map.of()));

        Props props = builder.build();
        assertTrue(props.keys().isEmpty());
    }

    @Test
    public void test_props() {
        assertEquals(builder, builder.props(Map.of(key, value)));

        Props props = builder.build();
        assertEquals(Set.of(key), props.keys());
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_props_exception() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put(key, null);
        map.put(null, value);

        assertEquals("null key", assertThrows(ValidationException.class, () -> builder.props(map)).getMessage());
        Props props = builder.build();
        assertTrue(props.keys().isEmpty());
    }
}
