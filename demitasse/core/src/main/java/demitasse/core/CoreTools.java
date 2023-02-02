package demitasse.core;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static demitasse.core.CoreConstants.EMPTY_CLASSES;
import static demitasse.core.CoreConstants.EMPTY_OBJECTS;
import static demitasse.core.CoreConstants.INSTANCE_ENUM;
import static demitasse.core.CoreConstants.INSTANCE_FIELD;
import static demitasse.core.CoreConstants.INSTANCE_METHOD;
import static java.lang.reflect.Modifier.isStatic;
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
     * @return true predicate
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> truePredicate() {
        return (Predicate<T>) PREDICATE_TRUE;
    }

    /**
     * @return false predicate
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> falsePredicate() {
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
     * @param object object
     */
    public static void suppressAccessControl(AccessibleObject object) {
        if (object == null) return;
        object.setAccessible(true);
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

    /**
     * @param object object
     * @return string
     */
    public static String toString(Object object) {
        return object == null ? null : String.valueOf(object);
    }

    /**
     * @param object object
     * @return boolean
     */
    public static Boolean toBoolean(Object object) {
        if (object == null) return null;
        if (object instanceof Boolean value) return value;
        String s = String.valueOf(object);
        return Boolean.parseBoolean(s);
    }

    /**
     * @param object object
     * @return byte
     */
    public static Byte toByte(Object object) {
        if (object == null) return null;
        if (object instanceof Byte value) return value;
        if (object instanceof Number value) return value.byteValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Byte.parseByte(s);
    }

    /**
     * @param object object
     * @return character
     */
    public static Character toCharacter(Object object) {
        if (object == null) return null;
        if (object instanceof Character value) return value;
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return s.charAt(0);
    }

    /**
     * @param object object
     * @return short
     */
    public static Short toShort(Object object) {
        if (object == null) return null;
        if (object instanceof Short value) return value;
        if (object instanceof Number value) return value.shortValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Short.parseShort(s);
    }

    /**
     * @param object object
     * @return integer
     */
    public static Integer toInteger(Object object) {
        if (object == null) return null;
        if (object instanceof Integer value) return value;
        if (object instanceof Number value) return value.intValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Integer.parseInt(s);
    }

    /**
     * @param object object
     * @return long
     */
    public static Long toLong(Object object) {
        if (object == null) return null;
        if (object instanceof Long value) return value;
        if (object instanceof Number value) return value.longValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Long.parseLong(s);
    }

    /**
     * @param object object
     * @return float
     */
    public static Float toFloat(Object object) {
        if (object == null) return null;
        if (object instanceof Float value) return value;
        if (object instanceof Number value) return value.floatValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Float.parseFloat(s);
    }

    /**
     * @param object object
     * @return double
     */
    public static Double toDouble(Object object) {
        if (object == null) return null;
        if (object instanceof Double value) return value;
        if (object instanceof Number value) return value.doubleValue();
        String s = String.valueOf(object);
        if (isEmpty(s)) return null;
        return Double.parseDouble(s);
    }

    /**
     * @param type type
     * @param name name
     * @return field
     */
    public static Field field(Class<?> type, String name) {
        validate(type, Objects::nonNull, "null type");
        validate(name, not(CoreTools::isBlank), "blank name");
        try {
            return type.getField(name);
        } catch (NoSuchFieldException e) {
            // ignore
        }
        return inspect(type, o -> {
            Field field;
            try {
                field = o.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                field = null;
            }
            return new DefaultInspectionResult<>(field);
        });
    }

    /**
     * @param type            type
     * @param name            name
     * @param parameter_types parameter types
     * @return method
     */
    public static Method method(Class<?> type, String name, Class<?>... parameter_types) {
        validate(type, Objects::nonNull, "null type");
        validate(name, not(CoreTools::isBlank), "blank name");
        Class<?>[] types = parameter_types == null ? EMPTY_CLASSES : parameter_types;
        try {
            return type.getMethod(name, types);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return inspect(type, o -> {
            Method method;
            try {
                method = o.getDeclaredMethod(name, types);
            } catch (NoSuchMethodException e) {
                method = null;
            }
            return new DefaultInspectionResult<>(method);
        });
    }

    /**
     * @param type            type
     * @param parameter_types parameter types
     * @param parameters      parameters
     * @return object
     */
    @SuppressWarnings("unchecked")
    public static <T> T object(Class<?> type, Class<?>[] parameter_types, Object[] parameters) {
        validate(type, Objects::nonNull, "null type");
        if (parameter_types == null) parameter_types = EMPTY_CLASSES;
        if (parameters == null) parameters = EMPTY_OBJECTS;
        try {
            Constructor<?> constructor = type.getDeclaredConstructor(parameter_types);
            suppressAccessControl(constructor);
            return (T) constructor.newInstance(parameters);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new CoreException(e);
        }
    }

    /**
     * @param type type
     * @return instance
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T instance(Class<?> type) {
        validate(type, Objects::nonNull, "null type");
        if (type.isEnum()) return (T) Enum.valueOf((Class<? extends Enum>) type, INSTANCE_ENUM);
        Method method = method(type, INSTANCE_METHOD);
        if (method != null && isStatic(method.getModifiers())) {
            suppressAccessControl(method);
            try {
                return (T) method.invoke(type);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new CoreException(e);
            }
        }
        Field field = field(type, INSTANCE_FIELD);
        if (field != null && isStatic(field.getModifiers())) {
            suppressAccessControl(field);
            try {
                return (T) field.get(type);
            } catch (IllegalAccessException e) {
                throw new CoreException(e);
            }
        }
        return object(type, null, null);
    }

    /**
     * @param type     type
     * @param function function
     * @return result
     */
    public static <T> T inspect(Class<?> type, Function<Class<?>, InspectionResult<? extends T>> function) {
        validate(type, Objects::nonNull, "null type");
        validate(function, Objects::nonNull, "null function");
        if (type == Object.class) return null;
        Set<Class<?>> set = new HashSet<>();
        List<Class<?>> list = new LinkedList<>();
        list.add(type);
        while (!list.isEmpty()) {
            Class<?> t = list.remove(0);
            if (!set.add(t)) continue;
            InspectionResult<? extends T> result = function.apply(t);
            if (result != null && result.found()) return result.result();
            int i = 0;
            Class<?> sc = t.getSuperclass();
            if (sc != null && sc != Object.class) list.add(i++, sc);
            for (Class<?> ic : t.getInterfaces()) list.add(i++, ic);
        }
        return null;
    }

    /**
     * InspectionResult
     */
    public interface InspectionResult<T> {
        boolean found();

        T result();
    }

    /**
     * DefaultInspectionResult
     */
    public static final class DefaultInspectionResult<T> implements InspectionResult<T> {
        private final boolean found;
        private final T result;

        /**
         * @param result result
         */
        public DefaultInspectionResult(T result) {
            this(result != null, result);
        }

        /**
         * @param found  found
         * @param result result
         */
        public DefaultInspectionResult(boolean found, T result) {
            this.found = found;
            this.result = result;
        }

        @Override
        public boolean found() {
            return found;
        }

        @Override
        public T result() {
            return result;
        }
    }

    //
    private CoreTools() {
    }
}
