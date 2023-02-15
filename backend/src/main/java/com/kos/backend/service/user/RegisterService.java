package com.kos.backend.service.user;

import java.util.Map;

public interface RegisterService {
    public Map<String, String> register(String username, String password, String ConfirmPassword);
}
