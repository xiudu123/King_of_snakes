import { GameMap } from "./GameMap";
import { SnakesDouble } from "./SnakesDouble";
// import { Wall } from "./Wall";

export class GameMapDouble extends GameMap {
    constructor(ctx, parent, store){
        super(ctx, parent);
        this.store = store;
        this.inner_walls_count = parseInt(Math.random() * 30) + 10;     
        // this.inner_walls_count = 0;   

        this.snakes = [
            new SnakesDouble({id: 0, color: "#4876ec", r: this.rows - 2, c: 1, eye_direction: 0}, this),
            new SnakesDouble({id: 1, color: "#f94848", r: 1, c: this.cols - 2, eye_direction: 2}, this),
        ]
    }

    add_listening_even(){
        this.ctx.canvas.focus();
        const [snake0, snake1] = this.snakes;
        this.ctx.canvas.addEventListener("keydown", e => {
            if(e.key === 'w') snake0.set_diretion(0);
            else if(e.key === 'a') snake0.set_diretion(1);
            else if(e.key === 's') snake0.set_diretion(2);
            else if(e.key === 'd') snake0.set_diretion(3);

            else if(e.key === "ArrowUp") snake1.set_diretion(0);
            else if(e.key === "ArrowLeft") snake1.set_diretion(1);
            else if(e.key === "ArrowDown") snake1.set_diretion(2);
            else if(e.key === "ArrowRight") snake1.set_diretion(3);
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