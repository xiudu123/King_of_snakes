package com.kos.matchingsystem.service.impl;

import com.kos.matchingsystem.service.MatchingService;
import com.kos.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    public static final MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        matchingPool.addPlayer(userId, rating);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        matchingPool.removePlayer(userId);
        return "remove player success";
    }
}
