package demitasse.core.util;

import demitasse.core.converter.Converter;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import static demitasse.core.CoreConstants.BRACKET_CLOSE;
import static demitasse.core.CoreConstants.BRACKET_OPEN;
import static demitasse.core.CoreConstants.COMMA;
import static demitasse.core.CoreConstants.NULL;

/**
 * ToStringConverter
 */
public enum ToStringConverter implements Converter<Object, String> {
    Instance;

    @Override
    public String convert(Object object) {
        if (object == null) return NULL;
        if (object instanceof Type type) return type.getTypeName();
        if (object.getClass().isArray()) {
            StringBuilder builder = new StringBuilder();
            builder.append(BRACKET_OPEN);
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                Object o = Array.get(object, i);
                String s = convert(o);
                if (i > 0) builder.append(COMMA);
                builder.append(s);
            }
            builder.append(BRACKET_CLOSE);
            return builder.toString();
        }
        return String.valueOf(object);
    }

    @Override
    public Class<Object> inputType() {
        return Object.class;
    }

    @Override
    public Class<String> outputType() {
        return String.class;
    }

    @Override
    public Converter<String, Object> reverse() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
