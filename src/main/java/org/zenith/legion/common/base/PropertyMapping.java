package org.zenith.legion.common.base;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyMapping {
    String value() default "";
}
