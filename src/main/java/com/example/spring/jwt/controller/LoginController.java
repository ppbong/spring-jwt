package com.example.spring.jwt.controller;

import com.example.spring.jwt.model.UserLogin;
import com.example.spring.jwt.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody UserLogin userLogin) {
        return loginService.userLogin(userLogin.getUsername(), userLogin.getPassword());
    }
}
