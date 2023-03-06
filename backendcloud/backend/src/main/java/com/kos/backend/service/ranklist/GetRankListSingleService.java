package com.kos.backend.service.ranklist;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface GetRankListSingleService {
    JSONObject getRankListSingle(Map<String, String> data);
}
