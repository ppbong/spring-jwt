package com.example.spring.jwt.controller;

import com.example.spring.jwt.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@CrossOrigin
public class OtherController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> test(HttpServletRequest request) {
        String token = request.getHeader("token");

        return tokenService.verifyToken(token);
    }
}
