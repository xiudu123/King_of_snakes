package com.kos.backend.consumer.utils.game.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerBase {
    protected Integer id;
    protected Integer sx;
    protected Integer sy;
    protected List<Integer> steps;

}
