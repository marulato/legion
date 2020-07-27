package org.zenith.legion.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

    private static final ThreadLocal<HttpSession> threadSession = new ThreadLocal<>();

    public static void setSession(HttpServletRequest request) {
        if (request != null) {
            threadSession.set(request.getSession());
        }
    }

    public static HttpSession getSession() {
        return threadSession.get();
    }

    public static void clear() {
        threadSession.remove();
    }
}
