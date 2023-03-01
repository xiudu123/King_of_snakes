package com.kos.backend.consumer.utils.game.map;

public class GameMapBase extends Thread{
    protected final Integer rows;
    protected final Integer cols;
    protected final int[][] g;
    protected int[][] dir = {
            {-1, 0}, // 上;
            {0, 1}, // 右;
            {1, 0}, // 下;
            {0, -1}, // 左;
    };
    GameMapBase(Integer rows, Integer cols){
        this.rows = rows;
        this.cols = cols;
        this.g = new int[rows][cols];
    }

    protected void create_walled(){ // 创建四周的围墙;
        for(int r = 0; r < this.rows; ++ r){
            for(int c = 0; c < this.cols; ++ c){
                g[r][c] = 0;
            }
        }
        for(int r = 0; r < this.rows; ++ r) this.g[r][0] = this.g[r][this.cols - 1] = 1;
        for(int c = 0; c < this.cols; ++ c) this.g[0][c] = this.g[this.rows - 1][c] = 1;
    }

}
