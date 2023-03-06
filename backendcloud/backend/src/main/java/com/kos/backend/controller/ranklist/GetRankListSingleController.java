package com.kos.backend.controller.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.pojo.User;
import com.kos.backend.service.ranklist.GetRankListSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetRankListSingleController {
    @Autowired
    private GetRankListSingleService getRankListSingleService;
    @GetMapping("/ranklist/single/get/")
    JSONObject getRankListSingle(@RequestParam Map<String, String> data){
        return getRankListSingleService.getRankListSingle(data);
    }
}
