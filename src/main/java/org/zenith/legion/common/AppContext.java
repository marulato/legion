package org.zenith.legion.common;

import org.zenith.legion.sysadmin.entity.UserRole;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

public class AppContext implements Serializable {

    private String loginId;
    private String domain;
    private String userType;
    private boolean isAdminRole;
    private boolean hasAdminRole;
    private UserRole currentRole;
    private List<UserRole> allRoles;

    public static final String APP_CONTEXT_KEY = "Legion_Web_Session_Context";
    private static final ThreadLocal<AppContext> webThreadContext = new ThreadLocal<>();
    private static final ThreadLocal<AppContext> localThreadContext = new ThreadLocal<>();

    public static AppContext getAppContextFromCurrentThread(boolean flag) {
        AppContext context = webThreadContext.get();
        if (context == null) {
            HttpSession session = SessionManager.getSession();
            if (session != null) {
                context = (AppContext) session.getAttribute(APP_CONTEXT_KEY);
                setWebThreadAppContext(context);
            }
        }
        if (context == null) {
            context = localThreadContext.get();
        }
        if (context == null && flag) {
            context = new AppContext();
            context.setLoginId("Dummy User");
            localThreadContext.set(context);
        }
        return context;
    }

    public static AppContext getAppContextFromCurrentThread() {
        return getAppContextFromCurrentThread(false);
    }

    public static void setLocalThreadAppContext(AppContext context) {
        if (context != null) {
            localThreadContext.set(context);
        }
    }

    public static void setWebThreadAppContext(AppContext context) {
        if (context != null) {
            webThreadContext.set(context);
        }
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isAdminRole() {
        return isAdminRole;
    }

    public void setAdminRole(boolean adminRole) {
        isAdminRole = adminRole;
    }

    public boolean isHasAdminRole() {
        return hasAdminRole;
    }

    public void setHasAdminRole(boolean hasAdminRole) {
        this.hasAdminRole = hasAdminRole;
    }

    public UserRole getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(UserRole currentRole) {
        this.currentRole = currentRole;
    }

    public List<UserRole> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<UserRole> allRoles) {
        this.allRoles = allRoles;
    }
}
