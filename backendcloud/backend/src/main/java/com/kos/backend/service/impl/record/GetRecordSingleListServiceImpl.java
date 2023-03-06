package com.kos.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.Map;

@Service
public class GetRecordSingleListServiceImpl implements GetRecordSingleListService {

    @Autowired
    private RecordSingleMapper recordSingleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getRecordSingleList(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl losingUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = losingUser.getUser();

        int page = Integer.parseInt(data.get("page"));
        QueryWrapper<RecordSingle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).orderByDesc("create_time");

        return getRecords(page, queryWrapper);
    }

    @Override
    public JSONObject getRecordSingleListAll(Map<String, String> data) {
        int page = Integer.parseInt(data.get("page"));
        QueryWrapper<RecordSingle> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        return getRecords(page, queryWrapper);
    }

    private JSONObject getRecords(int page, QueryWrapper<RecordSingle> queryWrapper){
        int pageSize = 3;
        IPage<RecordSingle> recordSingleIPage = new Page<>(page, pageSize);
//        List<RecordSingle> records = recordSingleMapper.selectList(queryWrapper);
        List<RecordSingle> records = recordSingleMapper.selectPage(recordSingleIPage, queryWrapper).getRecords();
        List<JSONObject> items = new ArrayList<>();
        for(RecordSingle record : records){
            User user = userMapper.selectById(record.getUserId());
            JSONObject item = new JSONObject();
            item.put("user_photo", user.getPhoto());
            item.put("user_username", user.getUsername());
            item.put("record", record);
            items.add(item);
        }
        JSONObject resp = new JSONObject();
        resp.put("records", items);
        resp.put("records_count", recordSingleMapper.selectCount(null));
        resp.put("page_size", pageSize);
        return resp;
    }

}
