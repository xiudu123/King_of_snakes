package com.kos.backend.controller.record;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.service.record.GetRecordDoubleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetRecordDoubleListController {
    @Autowired
    private GetRecordDoubleListService getRecordDoubleListService;

    @GetMapping("/record/double/get/all/")
    JSONObject getRecordDoubleListAll(@RequestParam Map<String, String> data){
        return getRecordDoubleListService.getRecordDoubleListAll(data);
    }
    @GetMapping("/record/double/get/user/")
    JSONObject getRecordDoubleList(@RequestParam Map<String, String> data){
        return getRecordDoubleListService.getRecordDoubleList(data);
    }
}
