package com.kos.backend.service.impl.ranklist;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import com.kos.backend.service.ranklist.GetRankListSingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetRankListSingleServiceImpl implements GetRankListSingleService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getRankListSingle() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("scores");
        List<User> users = userMapper.selectList(queryWrapper);
        for(User user: users) user.setPassword("");
        return users;
    }
}
