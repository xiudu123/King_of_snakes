import { AcGameObject } from "./AcGameObject";
import { Wall } from "./Wall";
export class GameMap extends AcGameObject{
    constructor(ctx, parent){
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13, this.cols = 14;
        this.walls = [];
        this.g = [[]];
    }
    
    render_walls(){ // 渲染障碍物;
        for(let r = 0; r < this.rows; ++ r){
            for(let c = 0; c < this.cols; ++ c){
                if(this.g[r][c] == 1) this.walls.push(new Wall(r, c, this));
            }
        }
    }

    start(){
        this.start_gamemap();
        this.render_walls();
    }



    update(){
        this.update_size();
        this.render();
        this.update_gamemap();
    }
    update_size(){
        this.L = parseInt(Math.min(this.parent.clientHeight / this.rows, this.parent.clientWidth / this.cols));
        this.ctx.canvas.height = this.rows * this.L;
        this.ctx.canvas.width = this.cols * this.L;
        
    }

    render(){
        const color_even = "#AAD751", color_odd = "#A2D149";
        for(let c = 0; c < this.cols; ++ c){
            for(let r = 0; r < this.rows; ++ r){
                if((r + c) % 2) this.ctx.fillStyle = color_even;
                else this.ctx.fillStyle = color_odd;

                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);

            }
        }
    }


    start_gamemap(){

    }
    update_gamemap(){

    }

}