<template>
    <div class="score">
        <div class="score-board">计分板</div>
        <div class="score-num" >分数： {{ $store.state.pkSingle.score}}</div>
    </div>
    <PlayGround />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import { useStore } from "vuex";
import { onBeforeMount, onBeforeUnmount } from "vue";
export default{
    components: {
        PlayGround,
    },

    setup() {
        const store = useStore();
        onBeforeMount(() => {
            store.commit("updateMoudle", "single");
        })

        onBeforeUnmount(() => {
            store.commit("updateMoudle", "none");
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