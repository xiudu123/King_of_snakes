package com.kos.backend.service.impl.user.Dynamic;

import com.kos.backend.mapper.DynamicMapper;
import com.kos.backend.pojo.Dynamic;
import com.kos.backend.pojo.User;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.user.Dynamic.AddDynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddDynamicServiceImpl implements AddDynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;
    @Override
    public Map<String, String> addDynamic(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl)authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String content = data.get("content");
        Map<String, String> map = new HashMap<>();
        if(content == null || content.length() == 0){
            map.put("message", "内容不能为空");
            return map;
        }
        if(content.length() >= 10000){
            map.put("message", "内容太长");
            return map;
        }

        Date now = new Date();
        Dynamic dynamic = new Dynamic(null, user.getId(), content, now, now);
        dynamicMapper.insert(dynamic);
        map.put("message", "success");
        return map;
    }
}
