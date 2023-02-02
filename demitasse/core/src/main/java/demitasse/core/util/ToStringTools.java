package demitasse.core.util;

import demitasse.core.converter.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Stream;

import static demitasse.core.CoreConstants.BRACE_CLOSE;
import static demitasse.core.CoreConstants.BRACE_OPEN;
import static demitasse.core.CoreConstants.COMMA;
import static demitasse.core.CoreConstants.EQUAL;
import static demitasse.core.CoreConstants.NULL;
import static demitasse.core.CoreConstants.UNSPECIFIED;
import static demitasse.core.CoreTools.instance;
import static demitasse.core.CoreTools.suppressAccessControl;
import static java.util.Comparator.comparing;

/**
 * ToStringTools
 */
public final class ToStringTools {
    /**
     * @param object object
     * @return string
     */
    public static String toString(Object object) {
        if (object == null) return NULL;
        if (object instanceof Type type) return type.getTypeName();
        Class<?> type = object.getClass();
        if (type.isArray()) return ToStringConverter.Instance.convert(object);
        StringBuilder builder = new StringBuilder();
        builder.append(type.getName());
        Map<String, String> map = new TreeMap<>();
        while (type != Object.class) {
            typeToString(object, type, map);
            type = type.getSuperclass();
        }
        if (!map.isEmpty()) {
            StringJoiner joiner = new StringJoiner(COMMA, BRACE_OPEN, BRACE_CLOSE);
            map.forEach((k, v) -> joiner.add(k + EQUAL + v));
            builder.append(joiner);
        }
        return builder.toString();
    }

    private static void typeToString(Object object, Class<?> type, Map<String, String> map) {
        Stream.of(type.getDeclaredFields())
                .sorted(comparing(Field::getName))
                .forEach(o -> fieldToString(object, o, map));
        Stream.of(type.getDeclaredMethods())
                .filter(o -> o.getReturnType() != void.class && o.getParameterCount() == 0)
                .sorted(comparing(Method::getName))
                .forEach(o -> methodToString(object, o, map));
    }

    @SuppressWarnings("rawtypes")
    private static void fieldToString(Object object, Field field, Map<String, String> map) {
        try {
            ToString annotation = field.getAnnotation(ToString.class);
            if (annotation != null && annotation.ignore()) return;
            suppressAccessControl(field);
            Object value = field.get(object);
            if (value == null && (annotation == null || annotation.ignoreNull())) return;
            String name = (annotation == null || UNSPECIFIED.equals(annotation.name())) ? field.getName() : annotation.name();
            Class<? extends Converter> converter_type = annotation == null ? ToStringConverter.class : annotation.converterType();
            valueToString(value, name, converter_type, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void methodToString(Object object, Method method, Map<String, String> map) {
        try {
            ToString annotation = method.getAnnotation(ToString.class);
            if (annotation == null || annotation.ignore()) return;
            suppressAccessControl(method);
            Object value = method.invoke(object);
            if (value == null && annotation.ignoreNull()) return;
            String name = UNSPECIFIED.equals(annotation.name()) ? method.getName() : annotation.name();
            valueToString(value, name, annotation.converterType(), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void valueToString(Object value, String name, Class<? extends Converter> converter_type, Map<String, String> map) {
        Converter converter = (converter_type == Converter.class || converter_type == ToStringConverter.class) ? ToStringConverter.Instance : instance(converter_type);
        Object v = converter.convert(value);
        String s = String.valueOf(v);
        map.putIfAbsent(name, s);
    }

    //
    private ToStringTools() {
    }
}
