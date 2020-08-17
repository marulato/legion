package org.zenith.legion.common.base;

import org.zenith.legion.common.utils.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class BaseDto implements Serializable, Cloneable {

    public <T> T mapParameters(Class<T> type, HttpServletRequest request) throws Exception {
        if (type != null) {
            T dto = type.getConstructor(new Class[]{}).newInstance();
            Field[] allFields = type.getDeclaredFields();
            for (Field field : allFields) {
                setValue(field, type, dto, request.getParameter(field.getName()));
            }
            return dto;
        }
        return null;
    }

    private static void setValue(Field field, Class<?> objClass, Object instance, Object value) throws Exception {
        String setter = "set";
        field.setAccessible(true);
        setter += StringUtils.capitalCharacter(field.getName(), 0);
        Method setterMethod = objClass.getDeclaredMethod(setter);
        int modifier = setterMethod.getModifiers();
        if (Modifier.isPublic(modifier) && !Modifier.isAbstract(modifier)
                && !Modifier.isStatic(modifier) && setterMethod.getReturnType() == field.getType()) {
            setterMethod.setAccessible(true);
            setterMethod.invoke(instance, value);
        } else {
            field.set(instance, value);
        }
    }
}
