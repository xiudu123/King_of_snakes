import { Snakes } from "./Snakes";
export class SnakesSingle extends Snakes{
    constructor(info, gamemap){
        super(info, gamemap);
        this.last_direction = this.direction;
        this.eat_food = false;
        this.gamemap = gamemap;
    }

    check_tail_increasing(){
        if(this.gamemap.check_eat_food(this.next_cell)) {
            return true;
        }
        return false;
        // if(this.eat_food){
        //     this.eat_food = false;
        //     return true;
        // }
        // return false;
    }

    update_snakes(){

    }

}