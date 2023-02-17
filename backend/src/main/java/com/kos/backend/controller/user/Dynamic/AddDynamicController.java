package com.kos.backend.controller.user.Dynamic;

import com.kos.backend.service.user.Dynamic.AddDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddDynamicController {
    @Autowired
    private AddDynamicService addDynamicService;

    @PostMapping("/dynamic/add/")
    public Map<String, String> addDynamic(@RequestParam Map<String, String> data){
        return addDynamicService.addDynamic(data);
    }
}
