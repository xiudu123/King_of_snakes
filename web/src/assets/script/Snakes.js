import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";
export class Snakes extends AcGameObject{
    constructor(info, gamemap){
        super();
        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;
        this.next_cell = null;
        this.cells = [new Cell(info.r, info.c)];
        this.seed = 5;
        this.direction = -1;  // 0:上 1:左 2:下 3:右; 
        this.status = "idle"; // idle静止, die死亡;
        this.dir = [
            [-1, 0],
            [0, -1],
            [1, 0],
            [0, 1],
        ]
        this.step = 0;

        this.eps = 1e-2;
        this.eye_direction = info.eye_direction;
        this.eye_dx = [
            [-1, 1],
            [-1, -1],
            [1, -1],
            [1, 1],
        ],
        this.eye_dy = [
            [-1, -1],
            [1, -1],
            [1, 1],
            [-1, 1],
        ]
    }

    start(){
        this.gamemap.g[this.cells[0].r][this.cells[0].c] = true;
    }

    set_diretion(d){
        this.direction = d;
    }

    check_valid(cell){
        return !this.gamemap.g[cell.r][cell.c];
    }

    next_step(){
        const d = this.direction;
        this.eye_direction = d;
        this.status = "move";
        this.next_cell = new Cell(this.cells[0].r + this.dir[d][0], this.cells[0].c + this.dir[d][1]);
        this.direction = -1;
        ++ this.step;

        const k = this.cells.length;
        for(let i = k; i > 0; -- i){
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));
        }

        if(!this.check_valid(this.next_cell)) {
            this.status = "die";
        }
    }

    update_move(){
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);
        const increasing_flag = this.check_tail_increasing();
        if(distance < this.eps) {
            this.cells[0] = this.next_cell;
            this.status = "idle";
            this.gamemap.g[this.cells[0].r][this.cells[0].c] = true;
            if(!increasing_flag) {
                const k = this.cells.length;
                this.gamemap.g[this.cells[k - 1].r][this.cells[k - 1].c] = false;
                this.cells.pop();
            }
            this.next_cell = null;
        } else {
            const move_distance = this.seed * this.timedelta / 1000;
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;
            if(!increasing_flag){
                const k = this.cells.length;
                const tail = this.cells[k - 1], tail_target = this.cells[k - 2];
                const tail_dx = tail_target.x - tail.x;
                const tail_dy = tail_target.y - tail.y;
                const tail_distance = Math.sqrt(tail_dx * tail_dx + tail_dy * tail_dy);
                tail.x += move_distance * tail_dx / tail_distance;
                tail.y += move_distance * tail_dy / tail_distance;
            }
        }

    }

    update(){
        
        this.update_snakes();
        if(this.status === 'move'){
            this.update_move();    
        }
        this.render();
    }

    render(){
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;
        ctx.fillStyle = this.color;

        if(this.status === "die") ctx.fillStyle = "white";

        for(let cell of this.cells){
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, 2 * Math.PI);
            ctx.fill();
        }

        for(let i = 1; i < this.cells.length; ++ i){
            const a = this.cells[i - 1], b = this.cells[i];
            if(Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps) continue;
            if(Math.abs(a.x - b.x) < this.eps){
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            }else{
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";
        for(let i = 0; i < 2; ++ i){
            const eye_x = (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L;
            const eye_y = (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L;

            ctx.beginPath();
            ctx.arc(eye_x, eye_y, L * 0.05, 0, Math.PI * 2);
            ctx.fill();
        }

    }

    update_snakes(){

    }

    check_tail_increasing(){
        return false;
    }

}