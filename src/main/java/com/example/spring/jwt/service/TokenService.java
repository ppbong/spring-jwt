package com.example.spring.jwt.service;

import com.example.spring.jwt.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {
    @Autowired
    TokenUtil tokenUtil;

    public String getToken(String userId, String userRole, String authCode) {
        return tokenUtil.getToken(userId, userRole, authCode);
    }

    public Map<String, String> verifyToken(String token) {
        return tokenUtil.parseToken(token);
    }
}
