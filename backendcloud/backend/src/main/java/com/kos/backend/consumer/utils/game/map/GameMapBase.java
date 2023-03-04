package com.kos.backend.consumer.utils.game.map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameMapBase extends Thread{
    protected Integer rows;
    protected Integer cols;
    protected int[][] g;



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

    protected String getStringG(){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < rows; ++ i){
            for(int j = 0; j < cols; ++ j){
                res.append(g[i][j]);
            }
        }
        return res.toString();
    }

}
