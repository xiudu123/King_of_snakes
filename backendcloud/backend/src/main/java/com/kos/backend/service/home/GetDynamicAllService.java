package com.kos.backend.service.home;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.pojo.Dynamic;

import java.util.List;

public interface GetDynamicAllService {
    public List<JSONObject> getDynamicAll();
}
