package com.kos.backend.consumer.utils.game.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerSingle extends PlayerBase{
    Integer score;
    List<Integer> foodX = new ArrayList<>();
    List<Integer> foodY = new ArrayList<>();
    public PlayerSingle(Integer id, Integer sx, Integer sy, List<Integer> steps, Integer score) {
        super(id, sx, sy, steps);
        this.score = score;

    }

    public void addScore(){
        score += 10;
    } // 加分;

    public String getStringFoodX(){
        StringBuilder temp = new StringBuilder();
        for(int d: foodX){
            temp.append(d);
            temp.append("#");
        }
        return temp.toString();
    }

    public String getStringFoodY(){
        StringBuilder temp = new StringBuilder();
        for(int d: foodY){
            temp.append(d);
            temp.append("#");
        }
        return temp.toString();
    }

}
