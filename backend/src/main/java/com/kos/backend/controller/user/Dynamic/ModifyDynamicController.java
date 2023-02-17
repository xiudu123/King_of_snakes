package com.kos.backend.controller.user.Dynamic;

import com.kos.backend.service.user.Dynamic.ModifyDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ModifyDynamicController {
    @Autowired
    private ModifyDynamicService modifyDynamicService;
    @PostMapping("/dynamic/modify/")
    public Map<String, String> modifyDynamic(@RequestParam Map<String, String> data){
        return modifyDynamicService.modifyDynamic(data);
    }
}
