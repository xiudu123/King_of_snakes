package com.kos.backend.service.impl.user.Dynamic;

import com.kos.backend.mapper.DynamicMapper;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.pojo.User;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.user.Dynamic.ModifyDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ModifyDynamicServiceImpl implements ModifyDynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public Map<String, String> modifyDynamic(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        int dynamicId = Integer.parseInt(data.get("id"));
        String content = data.get("content");
        if(content == null || content.length() == 0){
            map.put("message", "内容不能为空");
            return map;
        }
        if(content.length() > 10000){
            map.put("message", "内容太长");
            return map;
        }
        Dynamic dynamic = dynamicMapper.selectById(dynamicId);
        if(dynamic == null){
            map.put("message", "该动态不存在或已被删除");
            return map;
        }
        if(!Objects.equals(dynamic.getUserId(), user.getId())){
            map.put("message", "没有权限修改");
            return map;
        }
        Dynamic new_dynamic = new Dynamic(
                dynamic.getId(),
                dynamic.getUserId(),
                content,
                dynamic.getCreateTime(),
                new Date()
        );
        dynamicMapper.updateById(new_dynamic);
        map.put("message", "success");
        return map;
    }
}
