package demitasse.prop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@link AbstractPropsBuilder}
 */
@ExtendWith(MockitoExtension.class)
public final class AbstractPropsBuilderTest {
    @Mock
    private Props props;
    @Mock
    private Props parent;
    @Mock
    private PropsBuilder parent_builder;
    private CA ca;
    private String id;

    @BeforeEach
    public void beforeEach() {
        ca = new CA();
        id = "id";
    }

    @AfterEach
    public void afterEach() {
        ca.reset();
    }

    @Test
    public void test_id() {
        assertEquals(ca, ca.id(id));

        when(ca.mock.internalBuild(id, EmptyProps.Instance)).thenReturn(props);
        assertEquals(props, ca.build());
        assertEquals(props, ca.build());
    }

    @Test
    public void test_parent_null() {
        assertEquals(ca, ca.parent((Props) null));

        when(ca.mock.internalBuild(null, EmptyProps.Instance)).thenReturn(props);
        assertEquals(props, ca.build());
        assertEquals(props, ca.build());
    }

    @Test
    public void test_parent() {
        assertEquals(ca, ca.parent(parent));

        when(ca.mock.internalBuild(null, parent)).thenReturn(props);
        assertEquals(props, ca.build());
        assertEquals(props, ca.build());
    }

    @Test
    public void test_parent_builder_null() {
        assertEquals(ca, ca.parent((PropsBuilder) null));

        when(ca.mock.internalBuild(null, EmptyProps.Instance)).thenReturn(props);
        assertEquals(props, ca.build());
        assertEquals(props, ca.build());
    }

    @Test
    public void test_parent_builder() {
        assertEquals(ca, ca.parent(parent_builder));

        when(parent_builder.build()).thenReturn(parent);
        when(ca.mock.internalBuild(null, parent)).thenReturn(props);
        assertEquals(props, ca.build());
        assertEquals(props, ca.build());
    }

    //
    private static class CA extends AbstractPropsBuilder<CA> {
        private final AbstractPropsBuilder<?> mock;

        private CA() {
            mock = mock(AbstractPropsBuilder.class);
        }

        @Override
        protected Props internalBuild(String id, Props parent) {
            return mock.internalBuild(id, parent);
        }
    }
}
