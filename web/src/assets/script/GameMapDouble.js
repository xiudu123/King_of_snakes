import { GameMap } from "./GameMap";
// import { Wall } from "./Wall";

export class GameMapDouble extends GameMap {
    constructor(ctx, parent){
        super(ctx, parent);

        this.inner_walls_count = parseInt(Math.random() * 30) + 10;     
        // this.inner_walls_count = 50;   
    }


    check_connectivity(g, sx, sy, tx, ty){ // 判断两个起点是否连通;
        if(sx == tx && sy == ty) return true;
        g[sx][sy] = true;
        let dir = [[-1, 0], [0, 1], [1, 0], [0, -1]];
        for(let i = 0; i < 4; ++ i){
            let dx = sx + dir[i][0];
            let dy = sy + dir[i][1];
            if(g[dx][dy]) continue;
            if(this.check_connectivity(g, dx, dy, tx, ty)) return true;
        }
        return false;
    }

    create_walls(){ // 创建障碍物;
        let copy_g = JSON.parse(JSON.stringify(this.g));
        for(let cc = 0; cc < this.inner_walls_count; cc += 2){
            for(let i = 0; i < 1000; ++ i){
                // 随机障碍物;
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                if(copy_g[r][c] || copy_g[this.rows - 1 - r][this.cols - 1 - c]) continue; // this.rows, this.cols, this.g均继承自基类;

                if((r === this.rows - 2 && c === 1) || (r === 1 && c == this.cols - 2)) continue; // 排除起点;
                copy_g[r][c] = copy_g[this.rows - 1 - r][this.cols - 1 - c] = true;
                break;
            }
        }
        let c_g = JSON.parse(JSON.stringify(copy_g));
        if(!this.check_connectivity(c_g, this.rows - 2, 1, 1, this.cols - 2)) return false;
        this.g = copy_g;
        return true;
    }

    start_gamemap(){
        for(let i = 0; i < 1000; ++ i){
            if(this.create_walls()) break;
        }
        

    }
    update_gamemap(){
        
    }

}