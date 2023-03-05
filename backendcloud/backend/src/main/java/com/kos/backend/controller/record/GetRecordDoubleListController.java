package com.kos.backend.controller.record;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.service.record.GetRecordDoubleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetRecordDoubleListController {
    @Autowired
    private GetRecordDoubleListService getRecordDoubleListService;

    @GetMapping("/record/double/get/all/")
    List<JSONObject> getRecordDoubleListAll(){
        return getRecordDoubleListService.getRecordDoubleListAll();
    }
    @GetMapping("/record/double/get/user/")
    List<JSONObject> getRecordDoubleList(){
        return getRecordDoubleListService.getRecordDoubleList();
    }
}
