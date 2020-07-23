package org.zenith.legion.common.validation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateWithRegex {

    String regex();

    String message();

    String profile() default "";
}
