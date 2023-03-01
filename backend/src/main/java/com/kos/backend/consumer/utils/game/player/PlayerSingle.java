package com.kos.backend.consumer.utils.game.player;

import com.kos.backend.consumer.utils.game.Cell;
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
    List<Boolean> checkIncreasing = new ArrayList<>();
    List<Cell> cells = new ArrayList<>();
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, -1, 0, 1};
    public PlayerSingle(Integer id, Integer sx, Integer sy, List<Integer> steps, Integer score) {
        super(id, sx, sy, steps);
        this.score = score;
        checkIncreasing.add(false);
        cells.add(new Cell(sx, sy));
    }

    public List<Cell> getCells(){ // 获取蛇的身体;
//        int x = sx, y = sy;
//        res.add(new Cell(x, y));
//        for(int i = 0; i < steps.size(); ++ i){
//            int d = steps.get(i);
//            boolean flag_increasing = checkIncreasing.get(i);
//            x += dx[d];
//            y += dy[d];
//            res.add(new Cell(x, y));
//            if(!flag_increasing) res.remove(0);
//            System.out.println(res);
//        }
        return cells;
    }
    public Cell addCell(int nextStep){ // 增头;
//        steps.add(nextStep);
        Cell cell = cells.get(cells.size() - 1);
        int x = cell.getX() + dx[nextStep];
        int y = cell.getY() + dy[nextStep];
        cell = new Cell(x, y);
        cells.add(cell);
        return cell;
    }

    public Cell removeCell(){ // 去尾;
        Cell cell = cells.get(0);
        cells.remove(0);
        return cell;
    }
    public void addScore(){
        score += 10;
    }

}
