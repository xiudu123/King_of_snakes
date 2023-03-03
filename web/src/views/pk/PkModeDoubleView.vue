<template>
    <MatchGround v-if="$store.state.pkDouble.status === 'matching' " />
    <PlayGround v-else-if="$store.state.pkDouble.status === 'playing' "/>
    <ResultBoardDoubleVue v-if="$store.state.pkDouble.loser !== 'none' " />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import MatchGround from "@/components/MatchGround.vue"
import ResultBoardDoubleVue from "@/components/ResultBoardDouble.vue"
import { onBeforeMount, onBeforeUnmount } from "vue";
import { useStore } from "vuex";
export default{
    components: {
        PlayGround,
        MatchGround,
        ResultBoardDoubleVue,
    },

    setup() {
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        
        let socket = null;
        onBeforeMount(() => {
            
            store.commit("updateOpponent", {
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                username: "我的对手",
            });
            store.commit("updateMoudle", "double");
            socket = new WebSocket(socketUrl);
            
            socket.onopen = () => { // 当链接成功建立执行;
                console.log("connected!");
                store.commit("updateSocketDouble", socket);
            },
            socket.onmessage = msg => { // 当成功接受信息时执行;
                const data = JSON.parse(msg.data);
                console.log("receive message!");
                const game = store.state.pkDouble.gameObjectDouble;
                if(data.event === "start-matching-double"){
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    store.commit("updateGame", {
                        game_map: data.game_map,
                        a_id: data.a_id,
                        b_id: data.b_id,
                    });
                    // store.commit("updateStatus", "wait");
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");
                    }, 2000);
                }else if(data.event === "move-double"){
                    const [snake0, snake1] = game.snakes;
                    snake0.set_diretion(data.a_direction);
                    snake0.set_increasing(data.a_increasing);
                    snake1.set_diretion(data.b_direction);
                    snake1.set_increasing(data.b_increasing);
                }else if(data.event === "result-double"){
                    const [snake0, snake1] = game.snakes;
                    if(data.loser === "all" || data.loser === "A") snake0.status = "die";
                    if(data.loser === "all" || data.loser === "B") snake1.status = "die";
                    store.commit("updateLoser", data.loser);
                }
            },
            socket.onclose = () => { // 当链接关闭时执行的函数;
                console.log("disconnected!");
            }
        }),

        onBeforeUnmount(() => {
            store.commit("updateMoudle", "none");

            socket.close();
        });

    }

}

</script>


<style scoped>

</style>