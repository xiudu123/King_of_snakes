package com.kos.backend.consumer.utils.game;

import com.kos.backend.pojo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class MatchingPool {
    private final CopyOnWriteArraySet<User> matchPool = new CopyOnWriteArraySet<>();

    public void addPlayer(User user){matchPool.add(user);}

    public void removePlayer(User user){
        matchPool.remove(user);
    }

    public int playerSize(){return matchPool.size();}

    public List<User> startGamePlayer(){
        Iterator<User> iterator = matchPool.iterator();
        User a = iterator.next(), b = iterator.next();
        matchPool.remove(a);
        matchPool.remove(b);
        List<User> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        return list;
    }

}
