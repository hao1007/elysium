package demitasse.prop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link EmptyPropsBuilder}
 */
public final class EmptyPropsBuilderTest {
    @Test
    public void test() {
        assertEquals(EmptyProps.Instance, EmptyPropsBuilder.Instance.build());
    }
}
