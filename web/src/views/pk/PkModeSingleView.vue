<template>
    <PlayGround />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import { useStore } from "vuex";
import { onMounted, onUnmounted } from "vue";
export default{
    components: {
        PlayGround,
    },

    setup() {
        const store = useStore();
        store.commit("updateMoudle", "single");
        onMounted(() => {
            
        }),

        onUnmounted(() => {
            store.commit("updateMoudle", "");
        })
    }
}

</script>


<style scoped>

</style>