package com.kos.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface GetRankListDoubleService {
    JSONObject getRankListDouble(Map<String, String> data);
}
