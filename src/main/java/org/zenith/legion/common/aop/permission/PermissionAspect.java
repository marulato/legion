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
import org.zenith.legion.general.ex.PermissionDeniedException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class PermissionAspect {

    private static final Logger log = LoggerFactory.getLogger(PermissionAspect.class);
    @Around("@annotation(org.zenith.legion.common.aop.permission.RequiresRoles)")
    public Object checkRequiresRoles(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean hasPermission = true;
        Class<?> targetType = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = targetType.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        List<String> roleIds = Arrays.asList(requiresRoles.value());
        Logical logical = requiresRoles.logical();
        AppContext context = AppContext.getFromWebThread();
        if (context != null) {
            log.info("Checking User -> " + context.getLoginId());
            List<String> allRoles = new ArrayList<>();
            for (UserRole role : context.getAllRoles()) {
                allRoles.add(role.getId());
            }
            if (logical == Logical.AND) {
                if (!allRoles.containsAll(roleIds)) {
                    hasPermission = false;
                }
            } else if (logical == Logical.OR) {
                if (!roleIds.contains(context.getCurrentRole().getId())) {
                    hasPermission = false;
                }
            } else if(logical == Logical.NONE) {
                if (roleIds.contains(context.getCurrentRole().getId())) {
                    hasPermission = false;
                }
            }
            if (hasPermission) {
                return joinPoint.proceed();
            } else {
                throw new PermissionDeniedException();
            }
        } else {
            throw new PermissionDeniedException();
        }
    }

    @Around("@annotation(org.zenith.legion.common.aop.permission.RequiresLogin)")
    public Object checkRequiresLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetType = joinPoint.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = targetType.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        RequiresLogin requiresLogin = method.getAnnotation(RequiresLogin.class);
        AppContext context = AppContext.getFromWebThread();
        if (context == null || !context.isLoggedIn()) {
            throw new PermissionDeniedException();
        } else {
            return joinPoint.proceed();
        }
    }
}
