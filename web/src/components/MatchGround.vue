<template>
<div class="matching">
    <div class="row">
        <div class="col-6">
            <div class="user-photo">
                <img :src="$store.state.user.photo" alt="">
            </div>
            <div class="user-name"> {{ $store.state.user.username }}</div>
        </div>
        <div class="col-6">
            <div class="user-photo">
                <img :src="$store.state.pkDouble.opponent_photo" alt="">
            </div>
            <div class="user-name"> {{ $store.state.pkDouble.opponent_username }}</div>
        </div>
    </div>
    <div class="row">
        <div class="col-12" style="text-align: center; margin-top: 15vh;">
            <button type="button" class="btn btn-warning btn-lg" @click="click_btn"> {{ match_btn_info }}</button>
        </div>
    </div>
    
</div>
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';
export default {
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");

        const click_btn = () => {
            if(match_btn_info.value === "开始匹配"){
                match_btn_info.value = "取消匹配";
                store.state.pkDouble.socket.send(JSON.stringify({
                    event: "start-matching-double"
                }));
            }else if(match_btn_info.value === "取消匹配") {
                match_btn_info.value = "开始匹配";
                store.state.pkDouble.socket.send(JSON.stringify({
                    event: "stop-matching-double",
                }));
            }
        }

        return{
            match_btn_info,
            click_btn,
        }
    }
}
</script>

<style scoped>
div.matching {
    width: 60vw;
    height: 70vh;
    margin: 4vh auto;
    background-color: rgba(50, 50, 50, 0.5);
}

div.user-photo{
    margin-top: 10vh;
    text-align: center;
}
div.user-photo > img{
    border-radius: 50%;
    height: 25vh;
}
div.user-name{
    text-align: center;
    font-size: 24;
    font-weight: 600;
    color: white;
    margin-top: 2vh;
}
</style>