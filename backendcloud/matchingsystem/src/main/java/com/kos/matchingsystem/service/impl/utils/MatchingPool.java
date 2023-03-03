package com.kos.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{

    private static List<Player> players = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";

    @Autowired
    public void setRestTemplateConfig(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating){
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        }finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try {
            List<Player> newPlayers = new ArrayList<>();
            for(Player player : players){
                if(!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        }finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime(){
        for(Player player: players){
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean checkMatched(Player A, Player B){
        return true;
    }

    private void sendResult(Player A, Player B){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", A.getUserId().toString());
        data.add("b_id", B.getUserId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void matchPlayers(){
        boolean[] used = new boolean[players.size()];
        for(int i = 0; i < players.size(); ++ i){
            if(used[i]) continue;
            for(int j = i + 1; j < players.size(); ++ j){
                if(used[j]) continue;
                Player A = players.get(i), B = players.get(j);
                if(checkMatched(A, B)){
                    used[i] = used[j] = true;
                    sendResult(A, B);
                    break;
                }
            }
        }

        List<Player> newPlayer = new ArrayList<>();
        for(int i = 0; i < players.size(); ++ i){
            if(used[i]) continue;
            newPlayer.add(players.get(i));
        }
        players = newPlayer;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                increaseWaitingTime();
                matchPlayers();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
