package org.zenith.legion.common.persistant.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Persistant {

    String tableName();

    String auditTableName();

    String whereClause() default "";

    boolean auditColumns() default true;

}
