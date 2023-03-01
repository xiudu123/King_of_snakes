package com.kos.backend.consumer.utils.game.map;

import java.util.Random;

public class GameMapDouble extends GameMapBase {
    private final Integer inner_walls_count;
    public GameMapDouble(Integer rows, Integer cols) {
        super(rows, cols);
        Random random = new Random();
        inner_walls_count = random.nextInt(30) + 10;
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
    public int[][] getG(){
        return this.g;
    }

}
