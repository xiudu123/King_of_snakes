<template>
    
        <div class="row justify-content-md-center login">
            <div class="col-3">
                <ContentField>
                    <form @submit.prevent="login">
                        <div class="mb-3">
                            <label for="username" class="form-label">用户名</label>
                            <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">密码</label>
                            <input v-model="password" type="password" class="form-control" id="password" rows="3" placeholder="请输入密码">
                        </div>
                        <div class="error-message">{{ error_message }}</div>
                        <button type="submit" class="btn btn-primary">登录</button>
                    </form>
                </ContentField>
            </div>
        </div>
    
</template>

<script>
import ContentField from "@/components/ContentField.vue"
import { ref } from "vue";
import { useStore } from "vuex";
import router from "@/router/index";
export default{
    components: {
        ContentField,
    },

    setup(){
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');
        const login = () => {
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success(){
                    // console.log(resp);
                    store.dispatch("updateInfo", {
                        success(){
                            // console.log(resp);
                            router.push({name: store.state.views.last_view});
                        }
                    })
                },
                error(){
                    error_message.value = "用户名或密码错误";
                }
            })
        }

        return{
            username,
            password,
            error_message,
            login,
        }
    }

}

</script>


<style scoped>
button{
    width: 100%;
}
.error-message{
    color: red;
}
.login{
    margin-top: 15vh;
}
</style>