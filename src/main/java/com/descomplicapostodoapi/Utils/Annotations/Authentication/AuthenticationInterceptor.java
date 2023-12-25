package com.descomplicapostodoapi.Utils.Annotations.Authentication;

import com.descomplicapostodoapi.Services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AuthService authService;

    @Autowired
    public AuthenticationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod))
            return true;

        if (hasAuthenticationAnnotation(handlerMethod)) {
            String token = Optional.ofNullable(request.getHeader("Authorization"))
                    .map(authHeader -> authHeader.replaceFirst("Bearer ", ""))
                    .orElse(null);

            if (token == null || !authService.validateToken(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        return true;
    }

    private boolean hasAuthenticationAnnotation(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(Authentication.class) ||
                handlerMethod.getBeanType().isAnnotationPresent(Authentication.class);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
