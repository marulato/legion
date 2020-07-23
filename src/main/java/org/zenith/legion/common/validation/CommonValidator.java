package org.zenith.legion.common.validation;

import org.zenith.legion.common.utils.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {

    public static Map<String, List<String>> doValidation(Object obj, String profile) throws Exception {
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            Field[] allFileds = objClass.getDeclaredFields();
            Map<String, List<String>> validationMap = new HashMap<>();
            for (Field field : allFileds) {
                field.setAccessible(true);
                validationMap.put(field.getName(), new ArrayList<>());
                if (field.isAnnotationPresent(NotNull.class)) {
                    NotNull anno = field.getAnnotation(NotNull.class);
                    if (getValue(field, objClass, obj) == null && isProfileMatch(profile, anno.profile())) {
                        validationMap.get(field.getName()).add(field.getAnnotation(NotNull.class).message());
                    }
                }

                if (field.isAnnotationPresent(NotEmpty.class)) {
                    Object value = getValue(field, objClass, obj);
                    NotEmpty anno = field.getAnnotation(NotEmpty.class);
                    if (isProfileMatch(profile, anno.profile())) {
                        if (value == null || value instanceof String && ((String) value).isEmpty()) {
                            validationMap.get(field.getName()).add(field.getAnnotation(NotEmpty.class).message());
                        }
                    }
                }

                if (field.isAnnotationPresent(ValidateWithRegex.class)) {
                    Object value = getValue(field, objClass, obj);
                    ValidateWithRegex anno = field.getAnnotation(ValidateWithRegex.class);
                    if (isProfileMatch(profile, anno.profile())) {
                        if (!(field.getType() == String.class)) {
                            throw new ClassCastException("无法将" + field.getName() + "的类型: "
                                    + field.getType() + "转换为java.lang.String");
                        }
                        Pattern pattern = Pattern.compile(anno.regex());
                        if (value != null) {
                            Matcher matcher = pattern.matcher((String) value);
                            if (!matcher.matches()) {
                                validationMap.get(field.getName()).add(anno.message());
                            }
                        } else {
                            validationMap.get(field.getName()).add(anno.message());
                        }
                    }
                }

                if (field.isAnnotationPresent(ValidateWithMethod.class)) {
                    ValidateWithMethod anno = field.getAnnotation(ValidateWithMethod.class);
                    Object value = getValue(field, objClass, obj);
                    checkValidateWithMethod(field, anno, value, obj, objClass, validationMap, profile);
                }

                if (field.isAnnotationPresent(ValidateWithMethodList.class)) {
                    Object value = getValue(field, objClass, obj);
                    ValidateWithMethodList anno = field.getAnnotation(ValidateWithMethodList.class);
                    ValidateWithMethod[] validateWithMethods = anno.methodList();
                    for (ValidateWithMethod validateWithMethod : validateWithMethods) {
                        checkValidateWithMethod(field, validateWithMethod, value, obj, objClass, validationMap, profile);
                    }
                }
            }
            return validationMap;
        }
        return null;
    }

    private static boolean isProfileMatch(String profile, String annoProfile) {
        if (StringUtils.isEmpty(profile) && StringUtils.isEmpty(annoProfile))
            return true;
        if (StringUtils.isNotEmpty(profile) && StringUtils.isNotEmpty(annoProfile))
            return profile.equals(annoProfile);
        if (StringUtils.isEmpty(profile) && StringUtils.isNotEmpty(annoProfile))
            return false;
        return true;
    }

    private static Object getValue(Field field, Class objClass, Object instance) throws Exception {
        String getter = "get";
        Object value = null;
        field.setAccessible(true);
        if (field.getType() == boolean.class) {
            getter = "is";
        }
        getter += StringUtils.capitalCharacter(field.getName(), 0);
        Method getterMethod = objClass.getDeclaredMethod(getter, null);
        if (getterMethod != null) {
            int modifer = getterMethod.getModifiers();
            if (Modifier.isPublic(modifer) && !Modifier.isAbstract(modifer)
                    && !Modifier.isStatic(modifer) && getterMethod.getReturnType() == field.getType()) {
                getterMethod.setAccessible(true);
                value = getterMethod.invoke(instance, null);
            } else {
                value = field.get(instance);
            }
        }
        return value;
    }

    private static void checkValidateWithMethod(Field field, ValidateWithMethod anno, Object value, Object obj,
                                                Class objClass, Map<String, List<String>> validationMap, String profile) throws Exception {
        if (isProfileMatch(profile, anno.profile())) {
            String methodName = anno.method();
            Method method = objClass.getDeclaredMethod(methodName, field.getType());
            if (method != null && (method.getReturnType() == boolean.class || method.getReturnType() == Boolean.class)) {
                method.setAccessible(true);
                boolean result = (boolean) method.invoke(obj, value);
                if (!result) {
                    validationMap.get(field.getName()).add(anno.message());
                }
            } else {
                throw new NoSuchMethodException("未能找到符合要求的ValidateWithMethod描述的方法");
            }
        }
    }

}
