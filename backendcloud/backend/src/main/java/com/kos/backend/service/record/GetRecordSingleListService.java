package com.kos.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public interface GetRecordSingleListService {
    List<JSONObject> getRecordSingleList();
    List<JSONObject> getRecordSingleListAll();
}
