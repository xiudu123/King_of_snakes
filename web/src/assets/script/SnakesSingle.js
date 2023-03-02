import { Snakes } from "./Snakes";
export class SnakesSingle extends Snakes{
    constructor(info, store, gamemap){
        super(info, gamemap);
        this.last_direction = this.direction;
        this.eat_food = false;
        this.store = store;
        this.gamemap = gamemap;
    }

    check_tail_increasing(){
        if(this.store.state.pkSingle.increasing) {
            return true;
        }
        return false;
    }

    update_snakes(){

    }

}