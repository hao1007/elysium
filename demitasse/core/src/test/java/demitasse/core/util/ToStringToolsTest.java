package demitasse.core.util;

import demitasse.core.converter.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link ToStringTools}
 */
@SuppressWarnings("unused")
public final class ToStringToolsTest {
    private Type type;
    private Object object;

    @BeforeEach
    public void beforeEach() {
        type = Object.class;
        object = new Object();
    }

    @Test
    public void test_toString_null() {
        assertEquals("null", ToStringTools.toString(null));
    }

    @Test
    public void test_toString_type() {
        assertEquals(type.getTypeName(), ToStringTools.toString(type));
    }

    @Test
    public void test_toString_object() {
        assertEquals(object.getClass().getName(), ToStringTools.toString(object));
    }

    @Test
    public void test_toString() {
        CA ca = new CA();
        assertEquals(CA.class.getName() + "{FA6=fa6,fa3=null,fa4=fa4,fa5=fa5,fa7=fa7}", ToStringTools.toString(ca));

        CB cb = new CB();
        assertEquals(CB.class.getName() + "{MB7=mb7,mb3=mb3,mb6=null,mb8=mb8}", ToStringTools.toString(cb));
    }

    @Test
    public void test_toString_array() {
        Object[] array = {null, type, object};
        assertEquals(format("[null,%s,%s]", type.getTypeName(), object), ToStringTools.toString(array));
    }

    //
    private static class CA {
        private final Object fa0 = null;
        @ToString
        private final Object fa1 = null;
        @ToString(ignore = true)
        private final Object fa2 = "fa2";
        @ToString(ignoreNull = false)
        private final Object fa3 = null;
        private final Object fa4 = "fa4";
        @ToString
        private final Object fa5 = "fa5";
        @ToString(name = "FA6")
        private final Object fa6 = "fa6";
        @ToString(converterType = Converter.class)
        private final Object fa7 = "fa7";
        @ToString(converterType = EA.class)
        private final Object fa8 = "fa8";
    }

    //
    private static class CB {
        private Object mb0() {
            return "mb0";
        }

        @ToString
        private void mb1() {
        }

        @ToString
        private Object mb2(Object object) {
            return "mb2";
        }

        @ToString
        private Object mb3() {
            return "mb3";
        }

        @ToString
        public Object mb4() {
            return null;
        }

        @ToString(ignore = true)
        private Object mb5() {
            return "mb5";
        }

        @ToString(ignoreNull = false)
        private Object mb6() {
            return null;
        }

        @ToString(name = "MB7")
        private Object mb7() {
            return "mb7";
        }

        @ToString(converterType = Converter.class)
        private Object mb8() {
            return "mb8";
        }

        @ToString(converterType = EA.class)
        private Object mb9() {
            return "mb9";
        }
    }

    //
    private enum EA implements Converter<Object, Object> {
        Instance;

        @Override
        public Object convert(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Class<Object> inputType() {
            return Object.class;
        }

        @Override
        public Class<Object> outputType() {
            return Object.class;
        }

        @Override
        public Converter<Object, Object> reverse() {
            throw new UnsupportedOperationException();
        }
    }
}
