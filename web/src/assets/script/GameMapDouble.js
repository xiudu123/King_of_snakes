import { GameMap } from "./GameMap";
import { SnakesDouble } from "./SnakesDouble";
// import { Wall } from "./Wall";

export class GameMapDouble extends GameMap {
    constructor(ctx, parent, store){
        super(ctx, parent);
        this.store = store;
        this.rows = store.state.pkDouble.map_rows;
        this.cols = store.state.pkDouble.map_cols;
        this.snakes = [
            new SnakesDouble({id: 0, color: "#4876ec", r: this.rows - 2, c: 1, eye_direction: 0}, this),
            new SnakesDouble({id: 1, color: "#f94848", r: 1, c: this.cols - 2, eye_direction: 2}, this),
        ]
    }

    add_listening_even(){
        this.ctx.canvas.focus();
        this.ctx.canvas.addEventListener("keydown", e => {
            let d = -1;
            if(e.key === 'w') d = 0;
            else if(e.key === 'a') d = 1;
            else if(e.key === 's') d = 2;
            else if(e.key === 'd') d = 3;

            else if(e.key === "ArrowUp") d = 0;
            else if(e.key === "ArrowLeft") d = 1;
            else if(e.key === "ArrowDown") d = 2;
            else if(e.key === "ArrowRight") d = 3;
            
            if(d >= 0){
                console.log(d);
                this.store.state.pkDouble.socket.send(JSON.stringify({
                    event: "move-double",
                    direction: d,
                }));
            }
        });
    }

    start_gamemap(){
        this.g = this.store.state.pkDouble.game_map;
        this.add_listening_even();
    }

    check_ready(){
        for(const snake of this.snakes){
            if(snake.status !== "idle") return false;
            if(snake.direction === -1) return false;
        }
        
        return true;
    }

    next_step(){
        for(const snake of this.snakes){
            snake.next_step();
        }
    }

    update_gamemap(){
        if(this.check_ready()){
            this.next_step();
        }
    }

}