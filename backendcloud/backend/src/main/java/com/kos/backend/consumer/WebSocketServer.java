package com.kos.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.utils.game.GameDouble;
import com.kos.backend.consumer.utils.game.GameSingle;
import com.kos.backend.consumer.utils.JwtAuthentication;
import com.kos.backend.mapper.UserMapper;
import com.kos.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public static final ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();

    private User user;
    static private UserMapper userMapper;
    private Session session = null;
    private final GameSingle gameSingle = new GameSingle();
    public final GameDouble gameDouble = new GameDouble();

    @Autowired
    private void setUserMapper(UserMapper userMapper){ WebSocketServer.userMapper = userMapper; }
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
            gameDouble.stopMatching();
            users.remove(this.user.getId());
        }
    }


    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
//        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);

        String event = data.getString("event");
        if("start-matching-double".equals(event)){
            gameDouble.startMatching(user);
        }else if("stop-matching-double".equals(event)){
            gameDouble.stopMatching();
        } else if ("move-double".equals(event)) {
            gameDouble.moveDouble(data.getInteger("direction"));
        } else if("start-game-single".equals(event)){
            gameSingle.startGameSingle(user);
        }else if("move-single".equals(event)){
            gameSingle.moveSingle(data.getInteger("direction"));
        }else if("next-move-single".equals(event)){
            gameSingle.getRunSingle();
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