package com.kos.backend.service.impl.home;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.DynamicMapper;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.pojo.User;
import com.kos.backend.service.home.GetDynamicAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetDynamicAllServiceImpl implements GetDynamicAllService {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<JSONObject> getDynamicAll() {
        QueryWrapper<Dynamic> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Dynamic> list = dynamicMapper.selectList(queryWrapper);
        List<JSONObject> resp = new ArrayList<>();
        for(Dynamic dynamic: list){
            JSONObject object = new JSONObject();
            User user = userMapper.selectById(dynamic.getUserId());
            object.put("user_name", user.getUsername());
            object.put("user_photo", user.getPhoto());
            object.put("content", dynamic.getContent());
            object.put("modify_time", dynamic.getModifyTime());
            resp.add(object);
        }
        return resp;
    }
}
