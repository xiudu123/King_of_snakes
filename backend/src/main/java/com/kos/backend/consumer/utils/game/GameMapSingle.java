package com.kos.backend.consumer.utils.game;

import com.kos.backend.consumer.utils.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMapSingle extends GameMap{
    private Boolean food;
    private final Random random = new Random();
    public GameMapSingle(Integer rows, Integer cols) {
        super(rows, cols);
        food = false;
    }

    private void checkFoodExit(){
        if(!food){
            List<Cell> list = new ArrayList<>();
            for(int r = 0; r < this.rows; ++ r){
                for(int c = 0; c < this.cols; ++ c){
                    if(this.g[r][c] == 0) list.add(new Cell(r, c));
                }
            }
            if(list.size() >= 1){
                int x = random.nextInt(list.size());
                Cell _temp = list.get(x);
                this.g[_temp.getX()][_temp.getY()] = 2;
                food = true;
            }
        }
    }

    private Boolean checkEatFood(Cell cell){
        return this.g[cell.getX()][cell.getY()] == 2;
    }

    private void destroyFood(){
        this.food = false;
    }
    public void createMap(){
        create_walled();
        checkFoodExit();
    }
    public int[][] getG(){
        return this.g;
    }

}
