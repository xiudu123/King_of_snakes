package com.kos.backend.consumer.utils.game;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.WebSocketServer;
import com.kos.backend.consumer.utils.game.map.GameMapSingle;
import com.kos.backend.pojo.User;

public class GameSingle {
    private GameMapSingle gameMapSingle;
    private final Integer rows;
    private final Integer cols;
    public GameSingle(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
    }

    public void startGameSingle(User user){
        GameMapSingle gameMapSingle = new GameMapSingle(user.getId(), rows, cols);
        gameMapSingle.createMap();

        this.gameMapSingle = gameMapSingle;
        gameMapSingle.start();

        JSONObject object = new JSONObject();
        object.put("game_map", gameMapSingle.getG());
        object.put("event", "start-game-single");
        object.put("food_x", gameMapSingle.getFood().getX());
        object.put("food_y", gameMapSingle.getFood().getY());
        object.put("sx", gameMapSingle.getPlayer().getSx());
        object.put("sy", gameMapSingle.getPlayer().getSy());
        object.put("rows", rows);
        object.put("cols", cols);
        WebSocketServer.users.get(user.getId()).SendMessage(object.toJSONString());
    }

    public void moveSingle(int d){
        this.gameMapSingle.setNextStep(d);
    }

    public void getRunSingle(){
        this.gameMapSingle.run();
    }

}
