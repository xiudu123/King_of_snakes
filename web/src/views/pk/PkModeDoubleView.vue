<template>
    <PlayGround />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import { onMounted, onUnmounted } from "vue";
import { useStore } from "vuex";
export default{
    components: {
        PlayGround,
    },

    setup() {
        const store = useStore();
        store.commit("updateMoudle", "double");
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