package com.kos.backend.consumer.utils.game.player;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
//@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerDouble extends PlayerBase{

    public PlayerDouble(Integer id, Integer sx, Integer sy, List<Integer> steps){
        super(id, sx, sy, steps);
    }

    public Boolean check_increasing(){
        if(steps.size() <= 10) return true;
        return steps.size() % 3 == 1;
    }

}
