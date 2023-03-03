package com.kos.backend.service.impl.user.Dynamic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.DynamicMapper;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.pojo.User;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.user.Dynamic.DeleteDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class DeleteDynamicServiceImpl implements DeleteDynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public Map<String, String> deleteDynamic(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int id = Integer.parseInt(data.get("id"));
        Map<String, String> map = new HashMap<>();
        Dynamic _dynamic = dynamicMapper.selectById(id);
        if(_dynamic == null){
            map.put("message", "该动态不存在或已被删除");
            return map;
        }
        if(!Objects.equals(_dynamic.getUserId(), user.getId())){
            map.put("message", "没有权限进行该操作");
            return map;
        }
        dynamicMapper.deleteById(id);
        map.put("message", "success");
        return map;
    }
}
