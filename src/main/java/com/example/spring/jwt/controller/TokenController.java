package com.example.spring.jwt.controller;

import com.example.spring.jwt.service.LoginService;
import com.example.spring.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/token")
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/{authCode}", method = RequestMethod.GET)
    @ResponseBody
    public String token(@PathVariable String authCode) {
        if (!loginService.check("admin", authCode)) {
            return null;
        } else {
            return tokenService.getToken("admin", "admin", authCode);
        }
    }

    @RequestMapping(path = "/verify/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> verify(@PathVariable String token) {
        return tokenService.verifyToken(token);
    }
}
