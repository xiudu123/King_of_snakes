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
                <tr v-for="user in users" :key="user.id">
                    <td>
                        <img :src="user.photo" alt="" class="record-user-photo">
                        &nbsp;
                        <span class="record-user-username">{{ user.username }}</span>
                    </td>
                    <td>{{ user.scores }}</td>
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
        let users = ref([]);
        let current_page = 1;
        let total_users = 0;
        let pages = ref([]);
        let page_size = 0;

        const click_page = page => {
            let max_pages = parseInt(Math.ceil(total_users / page_size));
            if(page === -2) page = Math.max(current_page - 1, 1);
            if(page === -1) page = Math.min(current_page + 2, max_pages);
            if(1 <= page && page <= max_pages){
                pull_page(page);
            }
        }

        const update_pages = () => {
            let max_pages = parseInt(Math.ceil(total_users / page_size));
            let new_page = [];
            for(let i = current_page - 2; i <= current_page + 2; ++ i){
                if(1 <= i && i <= max_pages){
                    new_page.push({
                        number: i,
                        is_active: i === current_page ? "active" : "",
                    });
                }
            }
            pages.value = new_page;
        }

        const pull_page = page => {
            current_page = page;
            $.ajax({
                url: "http://127.0.0.1:3000/ranklist/single/get/",
                type: 'get',
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    page: page,
                },
                success(resp){
                    console.log(resp)
                    users.value = resp.users;
                    total_users = resp.users_count;
                    page_size = resp.page_size;
                    update_pages();
                },
                error(resp){
                    console.log(resp);
                }
            })
        };

        pull_page(current_page);
        return{
            users,
            pages,
            click_page,
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