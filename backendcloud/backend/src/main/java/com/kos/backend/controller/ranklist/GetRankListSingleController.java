package com.kos.backend.controller.ranklist;

import com.kos.backend.pojo.User;
import com.kos.backend.service.ranklist.GetRankListSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetRankListSingleController {
    @Autowired
    private GetRankListSingleService getRankListSingleService;
    @GetMapping("/ranklist/single/get/")
    List<User> getRankListSingle(){
        return getRankListSingleService.getRankListSingle();
    }
}
