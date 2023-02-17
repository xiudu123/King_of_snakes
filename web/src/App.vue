<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
// import $ from "jquery"
export default{
    components: {
      NavBar
    },

    // 测试后端API;
    setup(){

      // 注册;
      // $.ajax({
      //   url: "http://127.0.0.1:3000/user/account/register/",
      //   type: "post",
      //   data: {
      //     username: "xiu2",
      //     password: "123456",
      //     confirmPassword: "123456",
      //   },
      //   success(resp){
      //     console.log(resp);
      //   },
      //   error(resp){
      //     console.log(resp);
      //   }
      // })

      // 登录;
      // $.ajax({
      //   url: "http://127.0.0.1:3000/user/account/login/",
      //   type: "post",
      //   data: {
      //     username: "xiu1",
      //     password: "123456",
      //   },
      //   success(resp){
      //     console.log(resp);
      //   },
      //   error(resp){
      //     console.log(resp);
      //   }
      // });

      // 查看个人信息;
      // $.ajax({
      //   url: "http://127.0.0.1:3000/user/account/info/",
      //   type: "get",
      //   headers: {
      //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmN2JkMzYyMDkzYmU0MDJiOWNiM2EzMGNjNDM4MzE1OCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY3NjQ1MTg3NywiZXhwIjoxNjc3NjYxNDc3fQ.9vvBClWFtwH6aRnEtwJf7acmS-FB3dnyk_8aitQvMZc",
      //   },
      //   success(resp){
      //     console.log(resp);
      //   },
      //   error(resp){
      //     console.log(resp);
      //   }
      // })

    }

}
</script>
<style>
body{
  width: 100%;
  height: 100%;
  background: url("@/assets/image/Saito_Asuka.jpg");
  background-size: cover;
  position: absolute;
  z-index: -1;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
</style>
