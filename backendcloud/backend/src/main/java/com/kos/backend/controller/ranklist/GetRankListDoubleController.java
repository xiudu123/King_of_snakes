package com.kos.backend.controller.ranklist;

import com.kos.backend.pojo.User;
import com.kos.backend.service.ranklist.GetRankListDoubleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetRankListDoubleController {
    @Autowired
    private GetRankListDoubleService getRankListDoubleService;
    @GetMapping("/ranklist/double/get/")
    List<User> getRankListDouble(){
        return getRankListDoubleService.getRankListDouble();
    }
}
