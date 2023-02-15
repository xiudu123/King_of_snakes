package com.kos.backend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import com.kos.backend.service.user.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmPassword) {
        Map<String, String> map = new HashMap<>();
        if(username == null){
            map.put("message", "用户名不能为空");
            return map;
        }
        if(password == null || confirmPassword == null){
            map.put("message", "密码不能为空");
            return map;
        }
        username = username.trim(); // 去除两边空白字符;
        if(username.length() == 0){
            map.put("message", "用户名不能为空");
            return map;
        }
        if(password.length() == 0 || confirmPassword.length() == 0){
            map.put("message", "密码不能为空");
            return map;
        }
        if(!password.equals(confirmPassword)){
            map.put("message", "两次密码不一致");
            return map;
        }
        if(username.length() > 100){
            map.put("message", "用户名不能超过一百字符");
            return map;
        }
        if(password.length() > 100){
            map.put("message", "密码不能超过一百字符");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User _user = userMapper.selectOne(queryWrapper);
        if(_user != null){
            map.put("message", "该用户已存在");
            return map;
        }

        String encodePassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/70337_lg_6ddf5ad858.jpg";

        User user = new User(null, username, encodePassword, photo, 1500, 0);
        userMapper.insert(user);
        map.put("message", "success");
        return map;
    }
}
