// 每帧渲染一次;
const AC_GAME_OBJECTS = [];

export class AcGameObject {
    constructor(){
        AC_GAME_OBJECTS.push(this);
        this.timedelta = 0;
        this.has_call_start = false;// 表示当前对象是否执行过start()这个函数;
    }
    // 只在创建的时候执行一次;
    start(){

    }
    // 除第一次外，每一帧执行一次;
    update(){

    }
    // 删除之前执行;
    on_destory(){

    }

    destory(){
        this.on_destory();
        for(let i in AC_GAME_OBJECTS){
            const obj = AC_GAME_OBJECTS[i];
            if(obj === this){
                AC_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }

}

let last_timestamp;
const step = (timestamp) => {
    for(let obj of AC_GAME_OBJECTS){
        if(!obj.has_call_start){
            obj.start();
            obj.has_call_start = true;
        }else{
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step)