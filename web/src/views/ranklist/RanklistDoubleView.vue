<template>
    <RanklistBase>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>玩家</th>
                    <th>分数</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="ranklist in ranklists" :key="ranklist.id">
                    <td>
                        <img :src="ranklist.photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ ranklist.username }}</span>
                    </td>
                    <td>{{ ranklist.rating }}</td>
                </tr>
            </tbody>
        </table>
    </RanklistBase>
</template>

<script>
import RanklistBase from "@/components/RanklistBase.vue";
import $ from "jquery"
import { useStore } from "vuex";
import { ref } from "vue";
export default{
    components: {
        RanklistBase,
    },
    setup(){
        const store = useStore();
        let ranklists = ref([]);
        const pull = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/ranklist/double/get/",
                type: 'get',
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    console.log(resp)
                    ranklists.value = resp;
                },
                error(resp){
                    console.log(resp);
                }
            })
        };
        pull();
        return{
            ranklists,
        }
        
    }
}

</script>


<style scoped>
img.record-user-photo {
    width: 4vh;
    border-radius: 50%;
}
</style>