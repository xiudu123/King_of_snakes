package com.kos.backend.consumer.utils.game.map;

import com.alibaba.fastjson2.JSONObject;
import com.kos.backend.consumer.WebSocketServer;
import com.kos.backend.consumer.utils.game.Cell;
import com.kos.backend.consumer.utils.game.player.PlayerDouble;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GameMapDouble extends GameMapBase {
    private final Integer inner_walls_count;
    private final static int[][] dir = {
            {-1, 0}, // 上;
            {0, 1}, // 右;
            {1, 0}, // 下;
            {0, -1}, // 左;
    };
    Random random = new Random();
    private final ReentrantLock lock = new ReentrantLock();
    private final PlayerDouble playerA, playerB;
    private Integer nextStepA = null;
    private Integer nextStepB = null;
    private String status = "playing";
    private String loser;
    public GameMapDouble(Integer idA, Integer idB, Integer rows, Integer cols) {
        super(rows, cols);

        inner_walls_count = random.nextInt(30) + 10;
        playerA = new PlayerDouble(idA, rows - 2, 1, new ArrayList<>());
        playerB = new PlayerDouble(idB, 1, cols - 2, new ArrayList<>());
    }
    public int[][] getG(){
        return this.g;
    }
    public PlayerDouble getPlayerA(){return playerA;}
    public PlayerDouble getPlayerB(){return playerB;}

    public void setNextStepA(Integer nextStepA){
        System.out.println("setA direction: " + nextStepA);
        lock.lock();
        try {
            this.nextStepA = nextStepA;
        }finally {
            lock.unlock();
        }
    }
    public void setNextStepB(Integer nextStepB){
        System.out.println("setB direction: " + nextStepB);
        lock.lock();
        try {
            this.nextStepB = nextStepB;
        }finally {
            lock.unlock();
        }
    }

    private Boolean check_connectivity(int sx, int sy, int tx, int ty){ // 判断两起点是否连通;
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = 1;
        for(int i = 0; i < 4; ++ i){
            int x = sx + dir[i][0];
            int y = sy + dir[i][1];
            if(x >= 0 && x < this.rows && y >= 0 && y < this.cols && g[x][y] == 0){
                if(check_connectivity(x, y, tx, ty)){
                    g[sx][sy] = 0;
                    return true;
                }
            }
        }
        return false;
    }

    private Boolean create_walls(){ // 创建障碍物;
        create_walled();
        Random random = new Random();
        for(int cc = 0; cc < this.inner_walls_count; cc += 2){
            for(int i = 0; i < 1000; ++ i){
                // 随机障碍物;
                int r = random.nextInt(this.rows);
                int c = random.nextInt(this.cols);

                if(this.g[r][c] == 1 || this.g[this.rows - 1 - r][this.cols - 1 - c] == 1) continue;// this.rows, this.cols, this.g均继承自基类;
                if((r == this.rows - 2 && c == 1) || (r == 1 && c == this.cols - 2)) continue; // 排除起点;

                this.g[r][c] = this.g[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }
        return check_connectivity(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void createMap(){
        for(int i = 0; i < 1000; ++ i){
            if(this.create_walls()) break;
        }
    }

    private Boolean getNextStep(){
        for(int i = 0; i < 25; ++ i){
            try {
                Thread.sleep(200);
                lock.lock();
                try {
                    if(nextStepA != null && nextStepB != null) {
                        playerA.getSteps().add(nextStepA);
                        playerB.getSteps().add(nextStepB);
                        return true;
                    }
                }finally {
                    lock.unlock();
                }

            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
        return false;
    }

    private Boolean check_valid(Cell cellA, Cell cellB){
        if(cellA.getX() < 0 || cellA.getX() >= super.rows || cellA.getY() < 0 || cellA.getY() >= super.cols) return false;
        if(cellA == cellB) return false;
        return g[cellA.getX()][cellA.getY()] != 1 && g[cellA.getX()][cellA.getY()] != 2;
    }

    private void moveSnake(Cell addCell, PlayerDouble player){
        boolean creasing = player.check_increasing();
        this.g[addCell.getX()][addCell.getY()] = 2;
        if(!creasing){
            Cell removeCell = player.removeCell();
            this.g[removeCell.getX()][removeCell.getY()] = 0;
        }
        player.getCheckIncreasing().add(creasing);
    }

    void judge(){
        Cell addCellA = playerA.addCell(nextStepA);
        Cell addCellB = playerB.addCell(nextStepB);
        boolean validA = check_valid(addCellA, addCellB);
        boolean validB = check_valid(addCellB, addCellA);
        if(!validA || !validB){
            status = "finish";
            if(!validA && !validB) loser = "all";
            else if(!validA) loser = "A";
            else loser = "B";
        }
        moveSnake(addCellA, playerA);
        moveSnake(addCellB, playerB);
    }

    void sendAllMessage(String message){
        if(WebSocketServer.users.get(playerA.getId()) != null)
            WebSocketServer.users.get(playerA.getId()).SendMessage(message);

        if(WebSocketServer.users.get(playerB.getId()) != null)
            WebSocketServer.users.get(playerB.getId()).SendMessage(message);
    }

    void sendMove(){
        lock.lock();
        try {
            JSONObject object = new JSONObject();
            object.put("event", "move-double");
            object.put("a_direction", nextStepA);
            object.put("b_direction", nextStepB);
            object.put("a_increasing", playerA.check_increasing());
            object.put("b_increasing", playerB.check_increasing());
            nextStepA = nextStepB = null;
            sendAllMessage(object.toJSONString());
        }finally {
            lock.unlock();
        }
    }

    void sendResult(){
        JSONObject object = new JSONObject();
        object.put("event", "result-double");
        object.put("loser", loser);
        sendAllMessage(object.toJSONString());
    }

    @Override
    public void run() {
        for(int i = 0; i < 1000; ++ i){
            if(getNextStep()){
                judge();
                if("playing".equals(status)){
                    sendMove();
                }else{
                    sendResult();
                    break;
                }
            }else{
                status = "finish";
                lock.lock();
                try {
                    System.out.println(nextStepA + " ! " + nextStepB);
                    if(nextStepA == null && nextStepB == null) loser = "all";
                    else if(nextStepA == null) loser = "A";
                    else loser = "B";
                }finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }
        }
    }
}
