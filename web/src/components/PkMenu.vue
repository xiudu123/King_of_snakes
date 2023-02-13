<template>
    <div class="game-menu">
        <div class="game-menu-item" @click="open_single">个人模式</div>
        <br>
        <div class="game-menu-item" @click="open_double">双人模式</div>
    </div>
</template>

<script>
import router from "@/router/index";
import { useStore } from "vuex";
export default{
    setup() {
        const store = useStore();
        const open_single = () => {
            store.commit("updateMoudle", "single");
            router.push({
                name: "pk_single",
            })
        }
        const open_double = () => {
            store.commit("updateMoudle", "double");
            router.push({
                name: "pk_double",
            })
        }
        return{
            open_single,
            open_double,
        }
    }
}
</script>

<style scoped>
.game-menu{
    width: 20vw;
    position: relative;
    top: 40vh;
    left: 20vw;
    user-select: none;
}

.game-menu-item{
    background-color: rgba(39,21,28, 0.6);
    color: white;
    font-size: 6vh;
    text-align: center;
    font-style: italic;
    border-radius: 10px;
    letter-spacing: 0.5vw;
    cursor: pointer;
}
.game-menu-item:hover {
    transform: scale(1.2);
    transition: 200ms;
}
</style>