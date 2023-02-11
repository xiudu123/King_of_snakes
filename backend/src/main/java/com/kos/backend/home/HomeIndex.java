package com.kos.backend.home;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeIndex {
    @RequestMapping("/home/")
    public Map<String, String> getIndex(){
        Map<String, String> map = new HashMap<>();
        map.put("name1", "Hello");
        map.put("name2", "World");
        return map;
    }
}
