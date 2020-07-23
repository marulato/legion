package org.zenith.legion.common.validation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateWithMethod {

    String method();

    String message();

    String profile() default "";
}
