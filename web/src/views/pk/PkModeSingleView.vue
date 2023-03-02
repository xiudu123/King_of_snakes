<template>
    <MatchSingle v-if="$store.state.pkSingle.status === 'await'" />
    <div class="score" v-else>
        <div class="score-board">计分板</div>
        <div class="score-num" >分数： {{ $store.state.pkSingle.score}}</div>
    </div>
    <PlayGround v-if="$store.state.pkSingle.status !== 'await'" />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import MatchSingle from "@/components/MatchSingle.vue"
import { useStore } from "vuex";
import { onBeforeMount, onBeforeUnmount } from "vue";
export default{
    components: {
        PlayGround,
        MatchSingle,
    },

    setup() {
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket = null;
        onBeforeMount(() => {
            store.commit("updateMoudle", "single");
            store.commit("setScore", 0);
            
            socket = new WebSocket(socketUrl);
            
            socket.onopen = () => { // 当链接成功建立执行;
                console.log("connected!");
                store.commit("updateSocketSingle", socket);
            },
            socket.onmessage = msg => { // 当成功接受信息时执行;
                const data = JSON.parse(msg.data);
                console.log("receive message!");

                const game = store.state.pkSingle.gameObjectSingle;
                if(data.event === "start-game-single"){
                    store.commit("updateGamemapSingle", {
                        game_map: data.game_map,
                        rows: data.rows,
                        cols: data.cols,
                    });
                    store.commit("updateLocation", {
                        sx: data.sx,
                        sy: data.sy,
                    });
                    store.commit("updateFood", {
                        food_x: data.food_x,
                        food_y: data.food_y,
                    });

                    setTimeout(() => {
                        store.commit("updateStatusSingle", "playing");
                    }, 200);
                }else if(data.event === "move-single"){
                    game.snake.set_diretion(data.direction);
                    store.commit("setScore", data.score);
                    store.commit("updateFood", {
                        food_x: data.food_x,
                        food_y: data.food_y,
                    });
                    store.commit("updateIncreasing", data.increasing);
                }else if(data.event === "result-single"){
                    store.commit("updateStatusSingle", "finish");
                    
                    game.snake.status = "die";
                }
                
            },
            socket.onclose = () => { // 当链接关闭时执行的函数;
                console.log("disconnected!");
            }
        })

        onBeforeUnmount(() => {
            store.commit("updateMoudle", "none");
            store.commit("updateStatusSingle", "await");
            socket.close();
        });
    }
}
</script>

<style scoped>
.score{
    width: 10vw;
    height: 10vh;
    float: right;
    margin-right: 5vw;
    margin-top: 10vh;
    background-color: rgba(0, 0, 0, 0.3);
}
.score-board{
    text-align: center;
}
.score-num{
    font-size: 1.2em;
    margin-top: 1vh;
    color: white;
    text-align: center;
}
</style>