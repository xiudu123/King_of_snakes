<template>
    <div class="matching">
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
            let match_btn_info = ref("开始游戏");
    
            const click_btn = () => {
                if(match_btn_info.value === "开始游戏"){
                    match_btn_info.value = "等待";
                    store.state.pkSingle.socket.send(JSON.stringify({
                        event: "start-game-single"
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
    
    </style>