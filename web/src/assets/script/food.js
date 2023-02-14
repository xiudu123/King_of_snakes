import { AcGameObject } from "./AcGameObject";

export class Food extends AcGameObject{
    constructor(r, c, gamemap){
        super();
        this.r = r;
        this.c = c;
        this.x = this.c + 0.5;
        this.y = this.r + 0.5;
        this.gamemap = gamemap;
        this.color = "#FEDB01";
    }
    update(){
        this.render();
    }

    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        ctx.fillStyle = this.color;
        // ctx.fillRect(this.c * L, this.r * L, L, L);
        ctx.beginPath();
        ctx.arc(this.x * L, this.y * L, L / 2 * 0.8, 0, 2 * Math.PI);
        ctx.fill();
    }

}