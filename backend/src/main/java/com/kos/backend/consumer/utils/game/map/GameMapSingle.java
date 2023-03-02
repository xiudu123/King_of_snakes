package com.kos.backend.consumer.utils.game.map;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.WebSocketServer;
import com.kos.backend.consumer.utils.game.Cell;
import com.kos.backend.consumer.utils.game.player.PlayerSingle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameMapSingle extends GameMapBase{
    private Cell food;
    private final PlayerSingle player;
    private Integer nextStep = -1;
    private Integer lastStep = -1;
    private final ReentrantLock lock = new ReentrantLock();
    private String status = "playing";
    private final Random random = new Random();
    private final List<Integer> directions = new ArrayList<>();
    public GameMapSingle(Integer id, Integer rows, Integer cols) {
        super(rows, cols);

        food = null;

        // 初始蛇的位置;
        int r = random.nextInt(rows - 2) + 1;
        int c = random.nextInt(cols - 2) + 1;
        player = new PlayerSingle(id, r, c, new ArrayList<>(), 0);
        this.g[r][c] = 2;
    }

    public PlayerSingle getPlayer(){
        return player;
    }
    public Cell getFood(){
        return food;
    }
    public int[][] getG(){
        return this.g;
    } // 0空地, 1墙, 2蛇, 3食物;

    public void setNextStep(int nextStep){ // 设置蛇的下一个方向;
        lock.lock();
        try{
            directions.add(nextStep);
        }finally {
            lock.unlock();
        }
    }

    private void placeFood(){
        if(food == null){
            List<Cell> list = new ArrayList<>();
            for(int r = 0; r < this.rows; ++ r){
                for(int c = 0; c < this.cols; ++ c){
                    if(this.g[r][c] == 0) list.add(new Cell(r, c));
                }
            }
            if(list.size() >= 1){
                int x = random.nextInt(list.size());
                food = list.get(x);
                this.g[food.getX()][food.getY()] = 3;
            }
        }
    }

    private Boolean checkEatFood(Cell cell){
        return this.g[cell.getX()][cell.getY()] == 3;
    }

    private void destroyFood(){
        this.g[food.getX()][food.getY()] = 0;
        this.food = null;
    }
    public void createMap(){
        super.create_walled();
        placeFood();
    }


    private boolean getNextStep(){ // 是否获取下一步操作;
        lock.lock();
        try{
            if(directions.size() == 0) { nextStep = lastStep; }
            else{
                nextStep = directions.get(0);
                directions.remove(0);
            }

            if(nextStep != -1){
                player.getSteps().add(nextStep);
                lastStep = nextStep;
                return true;
            }
        }finally {
            lock.unlock();
        }
        return false;
    }

    private boolean check_valid(Cell cell){ // 判断下一步是否合法;
        if(cell.getX() < 0 || cell.getX() >= super.rows || cell.getY() < 0 || cell.getY() >= super.cols) return false;
        return g[cell.getX()][cell.getY()] != 1 && g[cell.getX()][cell.getY()] != 2;
    }

    private boolean check_food(Cell cell){ // 判断是否吃到食物;
        if(checkEatFood(cell)){
            destroyFood();
            placeFood();
            player.addScore();
            return true;
        }
        return false;
    }

    private void judge(){ // 判断下一步操作结果;
        Cell addCell = player.addCell(nextStep);

        boolean valid = check_valid(addCell);
        if(!valid){
            status = "finish";
            return;
        }

        boolean creasing = check_food(addCell);

        this.g[addCell.getX()][addCell.getY()] = 2;
        if(!creasing){
            Cell removeCell = player.removeCell();
            this.g[removeCell.getX()][removeCell.getY()] = 0;
        }
        player.getCheckIncreasing().add(creasing);
    }

    private void sendMove(){ // 传递移动信息;
        lock.lock();
        try {
            JSONObject object = new JSONObject();
            object.put("event", "move-single");
            object.put("direction", nextStep);
            object.put("score", player.getScore());
            object.put("food_x", food.getX());
            object.put("food_y", food.getY());
            object.put("increasing", player.getCheckIncreasing().get(player.getCheckIncreasing().size() - 1));
            nextStep = -1;
            WebSocketServer.users.get(player.getId()).SendMessage(object.toJSONString());
        }finally {
            lock.unlock();
        }
    }

    private void sendResult(){ // 公布结果;
        JSONObject object = new JSONObject();
        object.put("event", "result-single");
        object.put("score", player.getScore());
        WebSocketServer.users.get(player.getId()).SendMessage(object.toJSONString());
    }

    @Override
    public void run() {
        if(getNextStep()){
            judge();
            if("playing".equals(status)){
                sendMove();
            }else{
                sendResult();
            }
            nextStep = -1;
        }
    }
}
