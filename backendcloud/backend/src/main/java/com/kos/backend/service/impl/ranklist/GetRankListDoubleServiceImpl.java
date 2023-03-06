package com.kos.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import com.kos.backend.service.ranklist.GetRankListDoubleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetRankListDoubleServiceImpl implements GetRankListDoubleService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getRankListDouble(Map<String, String> data) {
        int pageSize = 3;
        int page = Integer.parseInt(data.get("page"));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> userIPage = new Page<>(page, pageSize);
        queryWrapper.orderByDesc("rating");

        List<User> users = userMapper.selectPage(userIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        for(User user: users) user.setPassword("");
        resp.put("users", users);
        resp.put("users_count", userMapper.selectCount(null));
        resp.put("page_size", pageSize);
        return resp;
    }
}
