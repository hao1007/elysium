package demitasse.core.util;

import demitasse.core.converter.Converter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static demitasse.core.CoreConstants.UNSPECIFIED;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ToString
 */
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface ToString {
    /**
     * @return name
     */
    String name() default UNSPECIFIED;

    /**
     * @return true if ignore, false otherwise
     */
    boolean ignore() default false;

    /**
     * @return true if ignore null, false otherwise
     */
    boolean ignoreNull() default true;

    /**
     * @return converter type
     */
    Class<? extends Converter> converterType() default ToStringConverter.class;
}
