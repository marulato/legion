package org.zenith.legion.common.aop.permission;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLogin {
}
