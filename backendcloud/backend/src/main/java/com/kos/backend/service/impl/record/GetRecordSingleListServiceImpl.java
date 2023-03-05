package com.kos.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.mapper.game.RecordSingleMapper;
import com.kos.backend.pojo.User;
import com.kos.backend.pojo.game.RecordSingle;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.record.GetRecordSingleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRecordSingleListServiceImpl implements GetRecordSingleListService {

    @Autowired
    private RecordSingleMapper recordSingleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<JSONObject> getRecordSingleList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl losingUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = losingUser.getUser();

        QueryWrapper<RecordSingle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).orderByDesc("create_time");

        return getRecords(queryWrapper);
    }

    @Override
    public List<JSONObject> getRecordSingleListAll() {
        QueryWrapper<RecordSingle> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        return getRecords(queryWrapper);
    }

    private List<JSONObject> getRecords(QueryWrapper<RecordSingle> queryWrapper){
        List<RecordSingle> records = recordSingleMapper.selectList(queryWrapper);
        List<JSONObject> items = new ArrayList<>();
        for(RecordSingle record : records){
            User user = userMapper.selectById(record.getUserId());
            JSONObject item = new JSONObject();
            item.put("user_photo", user.getPhoto());
            item.put("user_username", user.getUsername());
            item.put("record", record);
            items.add(item);
        }
        return items;
    }

}
