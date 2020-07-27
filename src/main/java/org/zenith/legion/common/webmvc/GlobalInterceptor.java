package org.zenith.legion.common.webmvc;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zenith.legion.common.AppContext;
import org.zenith.legion.common.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object context = session.getAttribute(AppContext.APP_CONTEXT_KEY);
        if (context != null) {
            SessionManager.setSession(request);
            AppContext.setWebThreadAppContext((AppContext) context);
            return true;
        } else {
            response.sendRedirect("/web/login");
        }
        return false;
    }

}
