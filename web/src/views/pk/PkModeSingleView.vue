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
                if(data.event === "start-game-single"){
                    store.commit("updateGamemapSingle", data.game_map);
                    console.log(data.game_map);
                }
                setTimeout(() => {
                    store.commit("updateStatusSingle", "playing");
                }, 200);
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