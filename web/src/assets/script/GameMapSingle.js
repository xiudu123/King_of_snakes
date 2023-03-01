// import { Cell } from "./Cell";
import { Food } from "./food";
import { GameMap } from "./GameMap";
import { SnakesSingle } from "./SnakesSingle";
export class GameMapSingle extends GameMap {
    constructor(ctx, parent, store){
        super(ctx, parent);
        this.ctx = ctx;
        this.store = store;

        this.snake = new SnakesSingle({id: 0, 
            color: "#4876ec", 
            r: store.state.pkSingle.sx, 
            c: store.state.pkSingle.sy, 
            eye_direction: 0}, 
            store,
            this);

        this.food = null;
        this.directions = [];
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
                this.store.state.pkSingle.socket.send(JSON.stringify({
                    event: "move-single",
                    direction: d,
                }));
            }
        });
    }

    start_gamemap(){
        this.g = this.store.state.pkSingle.game_map;
        this.food = new Food(this.store.state.pkSingle.food_x, this.store.state.pkSingle.food_y, this);
        this.add_listening_even();
    }

    destoer_food(){
        this.food.destory();
        this.food = null;
    }

    render_food(){
        if(this.store.state.pkSingle.increasing){
            this.destoer_food();
            this.food = new Food(this.store.state.pkSingle.food_x, this.store.state.pkSingle.food_y, this);
        }
    }

    check_ready(){
        if(this.snake.status !== "idle") return false;
        if(this.store.state.pkSingle.direction !== -1) return false;
        return true;
    }


    update_gamemap(){
        // this.g = this.store.state.pkSingle.game_map;
        if(this.check_ready()){
            // if(this.directions.length === 0) this.snake.direction = this.snake.last_direction;
            // else {
            //     this.snake.direction = this.directions[0];
            //     this.directions.splice(0);
            // }
            // this.snake.last_direction = this.snake.direction;
            // this.snake.next_step();
            this.store.state.pkSingle.socket.send(JSON.stringify({
                event: "next-move-single",
            }));
            
        }

        if(this.store.state.pkSingle.direction !== -1) {
            this.snake.direction = this.store.state.pkSingle.direction;
            
            this.render_food();
            this.snake.next_step();
            this.store.commit("updateDirection", -1);
        }
    }

}