<template>
    <div class="result-board">
        <div class="result-board-text">
            总分： {{ $store.state.pkSingle.score }}
        </div>
        <div class="result-board-btn" @click="restart">
            <button type="button" class="btn btn-warning btn-lg">
                再来一局
            </button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
export default{
    setup() {
        const store = useStore();
        const restart = () => {
            store.commit("updateStatusSingle", "await");
            store.commit("setScore", 0);
        }

        return{
            restart,
        }
    }
}
</script>

<style scoped>

.result-board{
    height: 30vh;
    width: 30vw;
    background-color: rgba(50, 50, 50, 0.5);
    position: absolute;
    top: 30vh;
    left: 35vw;
}

.result-board-text{
    text-align: center;
    color: white;
    font-size: 50px;
    font-weight: 600;
    font-style: italic;
    padding-top: 5vh;
}

.result-board-btn{
    padding-top: 7vh;
    text-align: center;
}

</style>