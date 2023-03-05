package com.kos.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public interface GetRecordDoubleListService {
    List<JSONObject> getRecordDoubleList();
    List<JSONObject> getRecordDoubleListAll();
}
