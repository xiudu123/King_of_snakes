<template>
    <MatchGround v-if="$store.state.pkDouble.status === 'matching' " />
    <PlayGround v-else-if="$store.state.pkDouble.status === 'playing' "/>
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import MatchGround from "@/components/MatchGround.vue"
import { onBeforeMount, onBeforeUnmount } from "vue";
import { useStore } from "vuex";
export default{
    components: {
        PlayGround,
        MatchGround,
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
                store.commit("updateSocket", socket);
            },
            socket.onmessage = msg => { // 当成功接受信息时执行;
                const data = JSON.parse(msg.data);
                console.log("receive message!");
                if(data.event === "start-matching"){
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");
                    }, 2000);
                    
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