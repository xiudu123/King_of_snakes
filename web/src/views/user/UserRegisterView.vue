<template>
    
    <div class="row justify-content-md-center register">
        <div class="col-4">
            <ContentField>
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" rows="3" placeholder="请输入密码">
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">确认密码</label>
                        <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" rows="3" placeholder="请再次输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">注册</button>
                </form>
            </ContentField>
        </div>
    </div>

</template>

<script>
import ContentField from "@/components/ContentField.vue"
import { ref } from "vue";
import $ from "jquery";
import router from "@/router/index";
export default{
components: {
    ContentField,
},

setup(){
    let username = ref('');
    let password = ref('');
    let confirmPassword = ref('');
    let error_message = ref('');

    const register = () => {
        $.ajax({
        url: "http://127.0.0.1:3000/user/account/register/",
        type: "post",
        data: {
          username: username.value,
          password: password.value,
          confirmPassword: confirmPassword.value,
        },
        success(resp){
            if(resp.message === "success"){
                router.push({name: "user_login"});
            }else error_message.value = resp.message;
          console.log(resp);
        },
        error(resp){
          console.log(resp);
        }
      })
    }

    return{
        username,
        password,
        error_message,
        confirmPassword,
        register,
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
.register{
    margin-top: 15vh;
}
</style>