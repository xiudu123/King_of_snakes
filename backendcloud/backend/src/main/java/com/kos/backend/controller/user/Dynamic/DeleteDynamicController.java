package com.kos.backend.controller.user.Dynamic;

import com.kos.backend.service.user.Dynamic.DeleteDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteDynamicController {
    @Autowired
    private DeleteDynamicService deleteDynamicService;
    @PostMapping("/dynamic/delete/")
    public Map<String, String> deleteDynamic(@RequestParam Map<String, String> data){
        return deleteDynamicService.deleteDynamic(data);
    }
}
