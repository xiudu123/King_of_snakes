package com.kos.backend.consumer.utils.game.player;

import com.kos.backend.consumer.utils.game.Cell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerBase {
    protected Integer id;
    protected Integer sx;
    protected Integer sy;
    int[] dx = {-1, 0, 1, 0};
    int[] dy = {0, -1, 0, 1};
    protected List<Integer> steps; // 每一步的移动方向;
    List<Boolean> checkIncreasing = new ArrayList<>(); // 每一步是否变长;
    List<Cell> cells = new ArrayList<>(); // 蛇的身体;
    public PlayerBase(int id, int sx, int sy, List<Integer> steps){
        this.id = id;
        this.sx = sx;
        this.sy = sy;
        this.steps = steps;
        checkIncreasing.add(false);
        cells.add(new Cell(sx, sy));
    }

    public Cell addCell(int nextStep){ // 增头;
        Cell lastCell = cells.get(cells.size() - 1);
        int x = lastCell.getX() + dx[nextStep];
        int y = lastCell.getY() + dy[nextStep];

        Cell newCell = new Cell(x, y);
        cells.add(newCell);
        return newCell;
    }
    public Cell removeCell(){ // 去尾;
        Cell cell = cells.get(0);
        cells.remove(0);
        return cell;
    }

    public String getStringSteps(){
        StringBuilder temp = new StringBuilder();
        for(int d: steps){
            temp.append(d);
        }
        return temp.toString();
    }

    public String getStringIncreasing(){
        StringBuilder temp = new StringBuilder();
        for(boolean increasing: checkIncreasing){
            temp.append((increasing ? 1 : 0));
        }
        return temp.toString();
    }

}
