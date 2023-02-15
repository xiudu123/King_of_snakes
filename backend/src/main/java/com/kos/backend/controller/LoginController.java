package com.kos.backend.controller;

import com.kos.backend.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/login/")
    public Map<String, String> getToken(@RequestParam Map<String, String> data){
        String username = data.get("username");
        String password = data.get("password");
        return loginService.getToken(username, password);
    }
}
