package com.kos.backend.controller;

import com.kos.backend.service.user.GetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetInfoController {
    @Autowired
    private GetInfoService getInfoService;

    @GetMapping("/user/account/info/")
    public Map<String, String> getInfo(){
        return getInfoService.getInfo();
    }
}
