package com.kos.backend.consumer.utils;

import com.kos.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;

// 根据token返回用户id;
public class JwtAuthentication {
    static public Integer getUserId(String token){
        int userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}
