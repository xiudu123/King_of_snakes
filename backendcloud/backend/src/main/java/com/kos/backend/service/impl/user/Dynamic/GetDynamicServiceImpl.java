package com.kos.backend.service.impl.user.Dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.DynamicMapper;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.pojo.User;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.user.Dynamic.GetDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDynamicServiceImpl implements GetDynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public List<Dynamic> getDynamic() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Dynamic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId()).orderByDesc("create_time");

        return dynamicMapper.selectList(queryWrapper);
    }
}
