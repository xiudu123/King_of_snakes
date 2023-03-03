package com.kos.backend.controller.user.Dynamic;

import com.kos.backend.pojo.Dynamic;
import com.kos.backend.service.user.Dynamic.GetDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetDynamicController {
    @Autowired
    private GetDynamicService getDynamicService;
    @GetMapping("/dynamic/get/")
    public List<Dynamic> getDynamic(){
        return getDynamicService.getDynamic();
    }
}
