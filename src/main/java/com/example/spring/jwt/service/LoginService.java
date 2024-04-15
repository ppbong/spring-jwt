package com.example.spring.jwt.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LoginService {
    private final Map<String, String> authCodeMap = new HashMap<>();

    /**
     * 用户登录
     * @param username 用户
     * @param password 口令
     * @return 授权码（用于获取token）
     */
    public String userLogin(String username, String password) {
        if (!"admin".equals(username) || !"admin".equals(password)) {
            // TODO 用户无效
            return null;
        }

        String authCode = authCodeMap.get("admin");

        if (authCode != null) {
            // TODO 多点登录
            return null;
        }

        Random random = new Random(System.currentTimeMillis());
        authCode = Long.toHexString(random.nextLong());
        authCodeMap.put("admin", authCode);

        return authCode;
    }

    /**
     * 验证授权码
     * @param username 用户
     * @param authCode 授权码
     * @return 结果
     */
    public boolean check(String username, String authCode) {
        String random = authCodeMap.get(username);

        return random != null && random.equals(authCode);
    }
}
