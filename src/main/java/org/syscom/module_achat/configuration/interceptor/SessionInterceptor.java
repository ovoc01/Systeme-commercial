package org.syscom.module_achat.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Check if the 'user' attribute exists in the session
        if (request.getSession().getAttribute("employe") == null) {
            // Redirect to the login page or handle the situation appropriately
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
    
}
