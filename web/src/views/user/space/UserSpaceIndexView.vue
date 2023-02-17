<template>
<UserSpaceBase>
    <div class="container">
        <div class="row">
            <div class="rol -3">
                <button type="button" class="btn btn-primary add-btn"  data-bs-toggle="modal"  data-bs-target="#add-dynamic-btn">
                    编写动态
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                    </svg>
                </button>
                <!-- Modal -->
                <div class="modal fade" id="add-dynamic-btn" tabindex="-1">
                    <div class="modal-dialog modal-xl">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5">编写动态</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <textarea v-model="content" id=""  rows="3" class="form-control" placeholder="分享你的观点吧"></textarea>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <div class="error_message"> {{ error_message }} </div>
                                <button type="button" class="btn btn-primary" @click="addDynamic">提交</button> 
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>

        <div v-for="post in posts" :key="post.id">
            <div class="card post">
                <div class="card-body">
                    <div>{{ post.content }}</div>
                    <button type="button" class="btn btn-danger post-delet"  data-bs-toggle="modal"  data-bs-target="#delete-dynamic-modal">删除</button>

                    <!-- Modal -->
                    <div class="modal fade" id="delete-dynamic-modal" tabindex="-1">
                        <div class="modal-dialog  modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5">是否确认删除</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="deleteDynamic(post)">确认删除</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    
                    <button type="button" class="btn btn-warning post-modify" data-bs-toggle="modal"  :data-bs-target="'#modify-dynamic-modal-' + post.id">修改</button>

                    <!-- Modal -->
                    <div class="modal fade" :id="'modify-dynamic-modal-' + post.id" tabindex="-1">
                        <div class="modal-dialog modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5">编写动态</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <textarea v-model="post.content" id=""  rows="3" class="form-control" placeholder="分享你的观点吧"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="error_message"> {{ post.error_message }} </div>
                                    <button type="button" class="btn btn-primary" @click="modifyDynamic(post)">提交</button> 
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div> 
                    </div>

                    
                </div>
            </div>
        </div>

    </div>
</UserSpaceBase>
</template>

<script>
import UserSpaceBase from "@/components/UserSpaceBase.vue"
import $ from "jquery";
import { useStore } from "vuex";
import { ref } from "vue";
import { Modal } from 'bootstrap/dist/js/bootstrap';
export default{
    components: {
        UserSpaceBase,
    },

    setup() {
        const store = useStore();
        let content = ref("");
        let error_message = ref("");
        let posts = ref([]);
        const refresh = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/dynamic/get/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp){
                    posts.value = resp;
                },
                error(resp){
                    console.log(resp);
                }
            })
        }
        refresh();

        const addDynamic = () => {
            error_message.value = "";
            $.ajax({
                url: "http://127.0.0.1:3000/dynamic/add/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    content: content.value,
                },
                success(resp){
                    if(resp.message === "success"){
                        Modal.getInstance("#add-dynamic-btn").hide();
                        content.value = "";
                        refresh();
                    }else{
                        error_message.value = resp.message;
                    }
                    
                },
                error(resp){
                    console.log(resp);
                }
            });
        }
        
        const modifyDynamic = (dynamic) => {
            $.ajax({
                url: "http://127.0.0.1:3000/dynamic/modify/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: dynamic.id,
                    content: dynamic.content,
                },
                success(resp){
                    if(resp.message === "success"){
                        Modal.getInstance('#modify-dynamic-modal-' + dynamic.id).hide();
                        refresh();
                    }else dynamic.error_message = resp.message;
                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        const deleteDynamic = (dynamic) => {
            $.ajax({
                url: "http://127.0.0.1:3000/dynamic/delete/",
                type: "post",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                data: {
                    id: dynamic.id,
                },
                success(resp){
                    if(resp.message === "success"){
                        Modal.getInstance('#delete-dynamic-modal').hide();
                        refresh();
                    }

                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        return{
            content,
            addDynamic,
            modifyDynamic,
            deleteDynamic,
            error_message,
            posts,
        }
    }

}

</script>


<style scoped>
.add-btn{
    float: right;
    margin-top: 1vh;
}
.error_message{
    color: red;
}
.post{
    margin: 10px;
}
.post-modify{
    float: right;
}
.post-delet{
    float: right;
    margin-left: 10px;
}


</style>