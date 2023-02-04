package demitasse.prop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * {@link CompositePropsBuilder}
 */
@ExtendWith(MockitoExtension.class)
public final class CompositePropsBuilderTest {
    @Mock
    private Props mock_props;
    @Mock
    private PropsBuilder mock_builder;
    private String key;
    private String value;
    private CompositePropsBuilder builder;

    @BeforeEach
    public void beforeEach() {
        key = "key";
        value = "value";
        builder = new CompositePropsBuilder();
    }

    @AfterEach
    public void afterEach() {
        builder.reset();
    }

    @Test
    public void test_append_props() {
        assertEquals(builder, builder.append((Props) null));
        assertEquals(builder, builder.append(mock_props));

        when(mock_props.prop(key)).thenReturn(value);
        Props props = builder.build();
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_append_builder() {
        assertEquals(builder, builder.append((PropsBuilder) null));
        assertEquals(builder, builder.append(mock_builder));

        when(mock_builder.build()).thenReturn(mock_props);
        when(mock_props.prop(key)).thenReturn(value);
        Props props = builder.build();
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_prepend_props() {
        assertEquals(builder, builder.prepend((Props) null));
        assertEquals(builder, builder.prepend(mock_props));

        when(mock_props.prop(key)).thenReturn(value);
        Props props = builder.build();
        assertEquals(value, props.prop(key));
    }

    @Test
    public void test_prepend_builder() {
        assertEquals(builder, builder.prepend((PropsBuilder) null));
        assertEquals(builder, builder.prepend(mock_builder));

        when(mock_builder.build()).thenReturn(mock_props);
        when(mock_props.prop(key)).thenReturn(value);
        Props props = builder.build();
        assertEquals(value, props.prop(key));
    }
}
