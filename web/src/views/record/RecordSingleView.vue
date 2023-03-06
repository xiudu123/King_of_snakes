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

        <nav aria-label="..." style="float: right">
            <ul class="pagination">
              <li class="page-item">
                <a class="page-link" @click="click_page(-2)">上一页</a>
              </li>

              <li :class="'page-item ' + page.is_active" v-for="page in pages" :key="page.number">
                <a class="page-link" href="#" @click="click_page(page.number)"> {{ page.number }} </a>
              </li>

              <li class="page-item">
                <a class="page-link" href="#" @click="click_page(-1)">下一页</a>
              </li>
            </ul>
          </nav>

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
        let current_page = 1;
        let page_size =  0;
        let total_records = 0;
        let pages = ref([]);

        const click_page = page => {
            let max_pages = parseInt(Math.ceil(total_records / page_size));
            if(page === -2) page = Math.max(current_page - 1, 1);
            if(page === -1) page = Math.min(current_page + 1, max_pages);
            if(1 <= page && page <= max_pages){
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_records / page_size));
            let new_page = [];
            for(let i = current_page - 2; i <= current_page + 2; ++ i){
                if(1 <= i && i <= max_pages){
                    new_page.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    })
                }
            }
            pages.value = new_page;
        }

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "http://127.0.0.1:3000/record/single/get/all/",
                type: "get",
                headers:{
                    Authorization: "Bearer " + store.state.user.token,
                },
                data:{
                    page: page,
                },
                success(resp){
                    records.value = resp.records;
                    page_size = resp.page_size;
                    total_records = resp.records_count;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            });
        }
        pull_page(current_page);
        return {
            records,
            click_page,
            pages,
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