package com.kos.backend.service.impl.user;

import com.kos.backend.pojo.User;
import com.kos.backend.service.impl.utils.UserDetailsImpl;
import com.kos.backend.service.user.LoginService;
import com.kos.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Map<String, String> getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password); // 将密码转化成密文;

        // 登录失败自动处理;
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        String jwt = JwtUtil.createJWT(user.getId().toString());

        Map<String, String> map = new HashMap<>();
        map.put("message", "success");
        map.put("token", jwt);
        return map;
    }
}
