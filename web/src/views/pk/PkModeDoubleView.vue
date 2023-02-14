<template>
    <PlayGround />
</template>

<script>
import PlayGround from "@/components/PlayGround.vue"
import { onBeforeMount, onBeforeUnmount } from "vue";
import { useStore } from "vuex";
export default{
    components: {
        PlayGround,
    },

    setup() {
        const store = useStore();

        onBeforeMount(() => {
            store.commit("updateMoudle", "double");
        }),

        onBeforeUnmount(() => {
            store.commit("updateMoudle", "none");
        });

    }

}

</script>


<style scoped>

</style>