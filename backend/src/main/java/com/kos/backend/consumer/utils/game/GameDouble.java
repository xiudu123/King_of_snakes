package com.kos.backend.consumer.utils.game;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.WebSocketServer;
import com.kos.backend.consumer.utils.game.map.GameMapDouble;
import com.kos.backend.pojo.User;

public class GameDouble {
    private final static MatchingPool matchingPool = new MatchingPool();
    private GameMapDouble gameMapDouble;
    User user;

    public void sendStartMessage(User playerA, User playerB, int idA, int idB){
        JSONObject resp = new JSONObject();
        resp.put("event", "start-matching-double");
        resp.put("opponent_username", playerB.getUsername());
        resp.put("opponent_photo", playerB.getPhoto());
        resp.put("game_map", gameMapDouble.getG());
        resp.put("a_id", idA);
        resp.put("b_id", idB);
        WebSocketServer.users.get(playerA.getId()).SendMessage(resp.toJSONString());
    }

    public void startMatching(User user){
        this.user = user;
        JSONObject object = new JSONObject();
        object.put("event", "start");
        WebSocketServer.users.get(user.getId()).SendMessage(object.toJSONString());
        matchingPool.addPlayer(user);

        System.out.println("start Matching");
    }

    public void stopMatching(User user){
        if(matchingPool.contains(user)){
            System.out.println("stop Matching");
            JSONObject object = new JSONObject();
            object.put("event", "stop");
            WebSocketServer.users.get(user.getId()).SendMessage(object.toJSONString());
            matchingPool.removePlayer(user);
            System.out.println("stop Matching");
        }

    }

    public void moveDouble(User user, int d){

        if(this.gameMapDouble.getPlayerA().getId().equals(user.getId())){
            System.out.println("A direction: " + d);
            gameMapDouble.setNextStepA(d);
        }else if(this.gameMapDouble.getPlayerB().getId().equals(user.getId())){
            gameMapDouble.setNextStepB(d);
        }
    }

    public static void startGame(User playerA, User playerB){
            GameMapDouble gameMapDouble = new GameMapDouble(playerA.getId(), playerB.getId(), 13, 14);
            gameMapDouble.createMap();
            gameMapDouble.start();
            WebSocketServer.users.get(playerA.getId()).gameDouble.gameMapDouble = gameMapDouble;
            WebSocketServer.users.get(playerB.getId()).gameDouble.gameMapDouble = gameMapDouble;
            WebSocketServer.users.get(playerA.getId()).gameDouble.sendStartMessage(playerA, playerB, playerA.getId(), playerB.getId());
            WebSocketServer.users.get(playerB.getId()).gameDouble.sendStartMessage(playerB, playerA, playerA.getId(), playerB.getId());
    }


}
