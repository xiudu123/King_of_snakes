package com.kos.backend.controller.user.account;

import com.kos.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/account/register/")
    public Map<String, String> register(@RequestParam Map<String, String> data){
        String username = data.get("username");
        String password = data.get("password");
        String confirmPassword = data.get("confirmPassword");
        return registerService.register(username, password, confirmPassword);
    }
}
