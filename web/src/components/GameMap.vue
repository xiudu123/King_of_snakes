<template>
    <div ref="parent" class="gamemap">
        <canvas ref="canvas" tabindex="0"> </canvas>
    </div>
</template>

<script>
import { GameMapDouble } from "@/assets/script/GameMapDouble";
import { GameMapSingle } from "@/assets/script/GameMapSingle";
import { ref, onMounted } from 'vue';
import { useStore } from "vuex";
export default{
    setup() {
        let parent = ref(null);
        let canvas = ref(null);
        const store = useStore();
        onMounted(() => {
            if(store.state.pkMode.mode === "double") {
                store.commit("updateGameObjectDouble", 
                    new GameMapDouble(canvas.value.getContext('2d'), parent.value, store)
                )
                console.log(store.state.pkDouble.gameObjectDouble);
            }
            else if(store.state.pkMode.mode === "single") {
                store.commit("updateGameObjectSingle", 
                    new GameMapSingle(canvas.value.getContext('2d'), parent.value, store)
                )
            }
        })

        return{
            parent,
            canvas
        }
    }
}
</script>


<style scoped>
.gamemap {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>

