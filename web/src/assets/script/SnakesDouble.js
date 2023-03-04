import { Snakes } from "./Snakes";
export class SnakesDouble extends Snakes{
    constructor(info, gamemap){
        super(info, gamemap);
        this.increasing = false;
    }

    check_tail_increasing(){
        return this.increasing;
    }

    set_increasing(increasing){
        this.increasing = increasing;
    }
    
    update_snakes(){
        
    }

}