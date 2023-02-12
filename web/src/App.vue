<template>
  <NavBar/>
  <router-view/>
</template>

<script>
import NavBar from "@/components/NavBar.vue"
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
export default{
    components: {
      NavBar
    }
}
</script>
<style>
body{
  background: url("@/assets/image/Saito_Asuka.jpg");
  background-size: cover;
}
</style>
