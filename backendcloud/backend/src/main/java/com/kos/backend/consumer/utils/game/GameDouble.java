package com.kos.backend.consumer.utils.game;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.WebSocketServer;
import com.kos.backend.consumer.utils.game.map.GameMapDouble;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class GameDouble {
    private GameMapDouble gameMapDouble;
    User user;
    private final static String addPlayerUrl = "http://127.0.0.1:3001/player/add/";
    private final static String removePlayerUrl = "http://127.0.0.1:3001/player/remove/";
    private static RestTemplate restTemplate;
    private static UserMapper userMapper;

    @Autowired
    private void setUserMapper(UserMapper userMapper){ GameDouble.userMapper = userMapper; }

    @Autowired
    private void setRestTemplate(RestTemplate restTemplate){GameDouble.restTemplate = restTemplate;}

    static public void sendStartMessage(User playerA, User playerB, int idA, int idB, int[][] g, int rows, int cols){
        JSONObject resp = new JSONObject();
        resp.put("event", "start-matching-double");
        resp.put("opponent_username", playerB.getUsername());
        resp.put("opponent_photo", playerB.getPhoto());
        resp.put("game_map", g);
        resp.put("a_id", idA);
        resp.put("b_id", idB);
        resp.put("rows", rows);
        resp.put("cols", cols);
        WebSocketServer.users.get(playerA.getId()).SendMessage(resp.toJSONString());
    }

    public void startMatching(User user){
        this.user = user;
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", user.getId().toString());
        data.add("rating", user.getRating().toString());
        restTemplate.postForObject(addPlayerUrl, data, String.class);
        System.out.println("start matching");
    }

    public void stopMatching(){
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", user.getId().toString());
        restTemplate.postForObject(removePlayerUrl, data, String.class);
        System.out.println("stop matching");
    }

    public void moveDouble(int d){
        if(this.gameMapDouble.getPlayerA().getId().equals(user.getId())){
            System.out.println("A direction: " + d);
            gameMapDouble.setNextStepA(d);
        }else if(this.gameMapDouble.getPlayerB().getId().equals(user.getId())){
            gameMapDouble.setNextStepB(d);
        }
    }

    public static void startGame(Integer aId, Integer bId){
        Integer rows = 13, cols = 14;
        User playerA = userMapper.selectById(aId), playerB = userMapper.selectById(bId);
        GameMapDouble gameMapDouble = new GameMapDouble(playerA.getId(), playerB.getId(), rows, cols);
        gameMapDouble.createMap();
        if(WebSocketServer.users.get(aId) != null)
            WebSocketServer.users.get(aId).gameDouble.gameMapDouble = gameMapDouble;
        if(WebSocketServer.users.get(bId) != null)
            WebSocketServer.users.get(bId).gameDouble.gameMapDouble = gameMapDouble;
        gameMapDouble.start();
        sendStartMessage(playerA, playerB, aId, bId, gameMapDouble.getG(), rows, cols);
        sendStartMessage(playerB, playerA, aId, bId, gameMapDouble.getG(), rows, cols);
    }

}
