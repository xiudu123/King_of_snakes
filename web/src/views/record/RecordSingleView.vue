<template>
    <RecordBase>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>玩家</th>
                    <th>分数</th>
                    <th>对战时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="record in records" :key="record.id">
                    <td>
                        <img :src="record.user_photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ record.user_username }}</span>
                    </td>
                    <td>{{ record.record.score }}</td>
                    <td>{{ record.record.createTime }}</td>
                    <td>
                        <button type="button" class="btn btn-warning">查看对局</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </RecordBase>
</template>

<script>
import RecordBase from "@/components/RecordBase.vue"
import $ from "jquery"
import { useStore } from "vuex"
import { ref } from "vue"
export default{
    components: {
        RecordBase,
    },
    setup() {
        const store = useStore();
        let records = ref([]);
        const pull = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/record/single/get/all/",
                type: "get",
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    records.value = resp;
                },
                error(resp){
                    console.log(resp);
                }
            });
        }
        pull();
        return {
            records,
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