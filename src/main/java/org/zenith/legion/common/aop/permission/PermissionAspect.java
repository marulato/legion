package org.zenith.legion.common.aop.permission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.sysadmin.entity.UserRole;
import org.zenith.legion.sysadmin.ex.PermissionDeniedException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class PermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);
    @Around("@annotation(org.zenith.legion.common.aop.permission.RequiresRoles)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        boolean hasPermission = true;
        Class<?> targetType = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = targetType.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        List<String> roleIds = Arrays.asList(requiresRoles.value());
        Logical logical = requiresRoles.logical();
        AppContext context = AppContext.getAppContextFromCurrentThread();
        if (context != null) {
            log.info("Checking User -> " + context.getLoginId());
            List<String> allRoles = new ArrayList<>();
            for (UserRole role : context.getAllRoles()) {
                allRoles.add(role.getRoleId());
            }
            if (logical == Logical.AND) {
                if (!allRoles.containsAll(roleIds)) {
                    hasPermission = false;
                }
            } else if (logical == Logical.OR) {
                allRoles.retainAll(roleIds);
                if (allRoles.isEmpty()) {
                    hasPermission = false;
                }
            } else if(logical == Logical.NONE) {
                allRoles.retainAll(roleIds);
                if (!allRoles.isEmpty()) {
                    hasPermission = false;
                }
            }
            if (hasPermission) {
                result = joinPoint.proceed();
            } else {
                throw new PermissionDeniedException();
            }
        } else {
            throw new PermissionDeniedException();
        }
        return result;
    }
}
