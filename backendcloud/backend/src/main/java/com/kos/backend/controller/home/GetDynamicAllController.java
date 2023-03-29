package com.kos.backend.controller.home;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.service.home.GetDynamicAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetDynamicAllController {
    @Autowired
    private GetDynamicAllService getDynamicAllService;

    @GetMapping("/home/dynamicall/get/")
    public List<JSONObject> getDynamicAll(){
        return getDynamicAllService.getDynamicAll();
    }
}
