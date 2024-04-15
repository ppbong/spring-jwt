package com.example.spring.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${token.secretKey}")
    private String secretKey;

    public String getToken(String userId, String userRole, String authCode) {
        Map<String,Object> headerClaimsMap = new HashMap<>();
        headerClaimsMap.put("alg", "HS256"); // algorithm
        headerClaimsMap.put("typ", "JWT"); // type

        return JWT.create()
                .withHeader(headerClaimsMap)
                .withClaim("userId", userId)
                .withClaim("userRole", userRole)
                .withClaim("authCode", authCode)
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
    }

    public Map<String, String> parseToken(String token) {
        Map<String, String> map = new HashMap<>();

        DecodedJWT decodedJWT = verifyToken(token);
        map.put("userId", decodedJWT.getClaim("userId").asString());
        map.put("userRole", decodedJWT.getClaim("userRole").asString());
        map.put("authCode", decodedJWT.getClaim("authCode").asString());
        map.put("timeStamp", decodedJWT.getClaim("timeStamp").asLong().toString());

        return map;
    }
}
