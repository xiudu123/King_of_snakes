package com.kos.backend.service.record;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;
import java.util.Map;

public interface GetRecordDoubleListService {
    JSONObject getRecordDoubleList(Map<String, String> data);
    JSONObject getRecordDoubleListAll(Map<String, String> data);
}
