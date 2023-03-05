package com.kos.backend.controller.record;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.service.record.GetRecordSingleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetRecordSingleListController {
    @Autowired
    private GetRecordSingleListService getRecordSingleListService;
    @GetMapping("/record/single/get/all/")
    List<JSONObject> getRecordSingleAll(){
        return getRecordSingleListService.getRecordSingleListAll();
    }
    @GetMapping("/record/single/get/user/")
    List<JSONObject> getRecordSingle(){
        return getRecordSingleListService.getRecordSingleList();
    }

}
