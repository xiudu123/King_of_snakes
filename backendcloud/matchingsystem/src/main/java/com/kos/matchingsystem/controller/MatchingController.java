package com.kos.matchingsystem.controller;

import com.kos.matchingsystem.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class MatchingController {

    @Autowired
    private MatchingService matchingService;
    @PostMapping("/player/add/")
    String addPlayer(@RequestParam MultiValueMap<String, String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        Integer rating = Integer.parseInt(Objects.requireNonNull(data.getFirst("rating")));
        return matchingService.addPlayer(userId, rating);
    }

    @PostMapping("/player/remove/")
    String removePlayer(@RequestParam MultiValueMap<String, String> data){
        Integer userId = Integer.parseInt(Objects.requireNonNull(data.getFirst("user_id")));
        return matchingService.removePlayer(userId);
    }
}
