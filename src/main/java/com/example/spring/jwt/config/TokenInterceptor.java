package com.example.spring.jwt.config;

import com.example.spring.jwt.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;
    @Value("${token.fresh}")
    private Long tokenFresh;
    @Value("${token.expire}")
    private Long tokenExpire;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get("userId");
        String userRole = map.get("userRole");
        String authCode = map.get("authCode");
        long timeout = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));

        if (timeout > tokenFresh && timeout < tokenExpire) {
            response.setHeader("token", tokenUtil.getToken(userId, userRole, authCode));
        } else if (timeout > tokenExpire) {
            throw new RuntimeException("Token Expired");
        }

        if (!"admin".equals(userId) || !"admin".equals(userRole)) {
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
