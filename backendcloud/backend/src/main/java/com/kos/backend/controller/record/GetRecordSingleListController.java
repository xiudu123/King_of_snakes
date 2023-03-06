package com.kos.backend.controller.record;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.service.record.GetRecordSingleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRecordSingleListController {
    @Autowired
    private GetRecordSingleListService getRecordSingleListService;
    @GetMapping("/record/single/get/all/")
    JSONObject getRecordSingleAll(@RequestParam Map<String, String> data){
        return getRecordSingleListService.getRecordSingleListAll(data);
    }
    @GetMapping("/record/single/get/user/")
    JSONObject getRecordSingle(@RequestParam Map<String, String> data){
        return getRecordSingleListService.getRecordSingleList(data);
    }

}
