<template>
  <div style="color:white">
    {{ name1 }}
    {{ name2 }}
  </div>
  <router-view/>
</template>

<script>
import { ref } from 'vue';
import $ from "jquery";

export default{
  
  setup: () => {
    let name1 = ref("");
    let name2 = ref("");
    $.ajax({
      url: "http://127.0.0.1:3000/home/",
      type: "get",
      success(resp){
        name1.value = resp.name1;
        name2.value = resp.name2;
      }
    })
    return {
      name1, 
      name2
    }
  },
  
}
</script>
<style>
body{
  background: url("@/assets/image/Saito_Asuka.jpg");
  background-size: cover;
}
</style>
