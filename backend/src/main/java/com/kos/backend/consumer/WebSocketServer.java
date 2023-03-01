package com.kos.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.utils.game.map.GameMapDouble;
import com.kos.backend.consumer.utils.JwtAuthentication;
import com.kos.backend.consumer.utils.game.MatchingPool;
import com.kos.backend.consumer.utils.game.map.GameMapSingle;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public static final ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private static final MatchingPool matchingPool = new MatchingPool();
    private User user;
    static private UserMapper userMapper;
    private Session session = null;
    private GameMapSingle gameMapSingle;
    @Autowired
    private void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        System.out.println("connected!");
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(user != null){
            users.put(userId, this);
        }else this.session.close();
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected!");
        if(this.user != null){
            users.remove(this.user.getId());
            matchingPool.removePlayer(this.user);
        }
    }

    private void startMatching(){
        JSONObject object = new JSONObject();
        object.put("event", "start");
        SendMessage(object.toJSONString());
        matchingPool.addPlayer(this.user);
        System.out.println("start Matching");
        while (matchingPool.playerSize() >= 2){
            List<User> player = matchingPool.startGamePlayer();
            User playerA = player.get(0), playerB = player.get(1);

            GameMapDouble gameMapDouble = new GameMapDouble(13, 14);
            gameMapDouble.createMap();

            JSONObject respA = new JSONObject();
            respA.put("event", "start-matching-double");
            respA.put("opponent_username", playerB.getUsername());
            respA.put("opponent_photo", playerB.getPhoto());
            respA.put("game_map", gameMapDouble.getG());
            users.get(playerA.getId()).SendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start-matching-double");
            respB.put("opponent_username", playerA.getUsername());
            respB.put("opponent_photo", playerA.getPhoto());
            respB.put("game_map", gameMapDouble.getG());
            users.get(playerB.getId()).SendMessage(respB.toJSONString());
        }
    }

    private void stopMatching(){
        JSONObject object = new JSONObject();
        object.put("event", "stop");
        SendMessage(object.toJSONString());
        matchingPool.removePlayer(this.user);
        System.out.println("stop Matching");
    }

    void startGameSingle(){
        GameMapSingle gameMapSingle = new GameMapSingle(user.getId(), 13, 14);
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
        SendMessage(object.toJSONString());
    }

    void moveSingle(int d){
        this.gameMapSingle.setNextStep(d);
    }

    void get_run(){
        this.gameMapSingle.get_run();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
//        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);

        String event = data.getString("event");
        if("start-matching-double".equals(event)){
            startMatching();
        }else if("stop-matching-double".equals(event)){
            stopMatching();
        }else if("start-game-single".equals(event)){
            startGameSingle();
        }else if("move-single".equals(event)){
            moveSingle(data.getInteger("direction"));
        }else if("next-move-single".equals(event)){
//            System.out.println("!!");
            get_run();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void SendMessage(String message){
        // 向Client发送消息;
        synchronized (this.session){
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}