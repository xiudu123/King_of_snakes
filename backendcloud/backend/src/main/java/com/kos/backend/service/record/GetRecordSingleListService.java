package com.kos.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;


public interface GetRecordSingleListService {
    JSONObject getRecordSingleList(Map<String, String> data);
    JSONObject getRecordSingleListAll(Map<String, String> data);
}
