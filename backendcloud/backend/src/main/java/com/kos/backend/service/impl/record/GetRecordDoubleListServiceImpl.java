package com.kos.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.mapper.game.PlayerMapper;
import com.kos.backend.mapper.game.RecordDoubleMapper;
import com.kos.backend.pojo.User;
import com.kos.backend.pojo.game.RecordDouble;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.record.GetRecordDoubleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetRecordDoubleListServiceImpl implements GetRecordDoubleListService {
    @Autowired
    private RecordDoubleMapper recordDoubleMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<JSONObject> getRecordDoubleListAll() {
        QueryWrapper<RecordDouble> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getRecords(queryWrapper);
    }

    @Override
    public List<JSONObject> getRecordDoubleList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<RecordDouble> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_a_id", user.getId()).or().eq("user_b_id", user.getId());
        queryWrapper.orderByDesc("create_time");
        return getRecords(queryWrapper);
    }

    private List<JSONObject> getRecords(QueryWrapper<RecordDouble> queryWrapper){
        List<RecordDouble> records = recordDoubleMapper.selectList(queryWrapper);
        List<JSONObject> items = new ArrayList<>();
        for(RecordDouble record : records){
            User A = userMapper.selectById(record.getUserAId());
            User B = userMapper.selectById(record.getUserBId());
            JSONObject item = new JSONObject();
            item.put("a_photo", A.getPhoto());
            item.put("a_username", A.getUsername());
            item.put("b_photo", B.getPhoto());
            item.put("b_username", B.getUsername());
            String result = "平局";
            if("A".equals(record.getLoser())) result = "B胜";
            else if("B".equals(record.getLoser())) result = "A胜";
            item.put("result", result);
            item.put("record", record);
            items.add(item);
        }
        return items;
    }

}
