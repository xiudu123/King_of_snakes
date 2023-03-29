<template>
<ContentFieldVue>
    <div class="container">
        
        <div v-for="post in posts" :key="post.id">
            
            <div class="card post">
                <div>
                    <img :src="post.user_photo" alt="" class="user-photo">
                    <span class="user-name">{{ post.user_name }}</span>
                    <span class="modify-time">{{ getDate(post.modify_time) }}</span>
                </div>
                <hr>
                <div class="card-body">
                    <div>{{ post.content }}</div>
                </div>
                <hr>
                <div class="row">
                <div class="col-4 hh" >点赞</div>
                <div class="col-4 hh" >评论</div>
                <div class="col-4 hh" >收藏</div>
                </div>
            </div>
        </div>

    </div>
</ContentFieldVue>
</template>

<script>
import $ from "jquery";
import { ref } from "vue";
import ContentFieldVue from '@/components/ContentField.vue';
export default{
    components: {
        ContentFieldVue,
    },

    setup() {
        let posts = ref([]);
        const refresh = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/home/dynamicall/get/",
                type: "get",
                success(resp){
                    console.log(resp);
                    posts.value = resp;
                    
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        const getDate = date => {
            let modify_date = new Date(date);
            return modify_date.toLocaleString('chinese',{ hour12: false });
        }
        refresh();

        return{
            posts,
            getDate,
        }
    }

}

</script>


<style scoped>
.user-photo {
    width: 6vh;
    border-radius: 50%;
}
.user-name{
    margin-left: 1vw;
}
hr{
    margin: 0 0;
}
.post{
    margin: 10px;
}
.modify-time{
    float: right;
}
.hh{
    text-align: center;
}
</style>