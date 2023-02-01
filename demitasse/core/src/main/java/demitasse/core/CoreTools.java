package demitasse.core;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

/**
 * CoreTools
 */
public final class CoreTools {
    private static final Predicate<?> PREDICATE_TRUE = o -> true;
    private static final Predicate<?> PREDICATE_FALSE = o -> false;
    private static final Map<Class<?>, Object> DEFAULT_VALUES;
    private static final Map<String, Class<?>> PRIMITIVES;

    static {
        DEFAULT_VALUES = Map.of(boolean.class, false, byte.class, (byte) 0, char.class, '\u0000', short.class, (short) 0, int.class, 0, long.class, 0L, float.class, 0.0f, double.class, 0.0);
        PRIMITIVES = DEFAULT_VALUES.keySet().stream().collect(toMap(Class::getName, identity()));
    }

    /**
     * @return predicate true
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> predicateTrue() {
        return (Predicate<T>) PREDICATE_TRUE;
    }

    /**
     * @return predicate false
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> predicateFalse() {
        return (Predicate<T>) PREDICATE_FALSE;
    }

    /**
     * @param collection collection
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * @param map map
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * @param string string
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * @param string string
     * @return true if null, empty, or blank, false otherwise
     */
    public static boolean isBlank(String string) {
        return isEmpty(string) || string.isBlank();
    }

    /**
     * @param object    object
     * @param predicate predicate
     * @param message   message
     * @return object
     */
    public static <T> T validate(T object, Predicate<? super T> predicate, String message) {
        return validate(object, predicate, () -> message);
    }

    /**
     * @param object    object
     * @param predicate predicate
     * @param supplier  supplier
     * @return object
     */
    public static <T> T validate(T object, Predicate<? super T> predicate, Supplier<String> supplier) {
        return validate(object, predicate, o -> {
            String message = supplier == null ? null : supplier.get();
            return message == null ? null : new ValidationException(message);
        });
    }

    /**
     * @param object    object
     * @param predicate predicate
     * @param function  function
     * @return object
     */
    public static <T, E extends Throwable> T validate(T object, Predicate<? super T> predicate, Function<? super T, E> function) throws E {
        if (predicate == null || predicate.test(object)) return object;
        E e = function == null ? null : function.apply(object);
        if (e == null) throw new ValidationException("invalid object");
        throw e;
    }

    /**
     * @param type type
     * @return default value
     */
    @SuppressWarnings("unchecked")
    public static <T> T defaultValue(Class<?> type) {
        validate(type, Objects::nonNull, "null type");
        Object value = DEFAULT_VALUES.get(type);
        if (value != null) return (T) value;
        if (type.isArray()) return (T) Array.newInstance(type.getComponentType(), 0);
        return null;
    }

    /**
     * @param name name
     * @return type
     */
    public static Class<?> type(String name) {
        validate(name, not(CoreTools::isBlank), "blank name");
        return toClass(name);
    }

    /**
     * @param object object
     * @return class
     */
    public static Class<?> toClass(Object object) {
        if (object == null) return null;
        if (object instanceof Class<?>) return (Class<?>) object;
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        Class<?> c = PRIMITIVES.get(s);
        try {
            return c == null ? Class.forName(s) : c;
        } catch (ClassNotFoundException e) {
            throw new CoreException(e);
        }
    }

    //
    private CoreTools() {
    }
}
