import { Snakes } from "./Snakes";
export class SnakesDouble extends Snakes{
    constructor(info, gamemap){
        super(info, gamemap);
    }

    check_tail_increasing(){
        if(this.step <= 10) return true;
        if(this.step % 3 === 1) return true;
        return false;
    }

    
    update_snakes(){
        
    }

}