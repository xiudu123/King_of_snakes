<template>
    <div class="container" >
        <div class="row">
            <div class="col-3 information">
                <ContentField>
                <img class="information-photo" :src="$store.state.user.photo" alt="">
                <hr>
                <div class="information-username"> {{ $store.state.user.username }} </div>
                <hr>
                <div class="information-scores">score : {{ $store.state.user.scores }}</div>
                <div class="information-rating">rating: {{ $store.state.user.rating }}</div>
                </ContentField>
            </div>
            <div class="col-9 ">
                <ContentField>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/user/space/space/" :class="route_name === 'user_space_space' || route_name === 'user_index' ? 'active': '' ">空间</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/user/space/pksingle/" :class="route_name === 'user_space_pksingle' ? 'active': '' ">个人对战信息</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/user/space/pkdouble/" :class="route_name === 'user_space_pkdouble' ? 'active': '' ">双人对战信息</a>
                        </li>
                    </ul>
                    <slot></slot>
                </ContentField>
            </div>
        </div>
    </div>
</template>

<script>
import ContentField from "@/components/ContentField.vue"
import { useRoute } from "vue-router";
import { computed } from 'vue';
export default{
    components: {
        ContentField,
    },
    setup(){
        const route = useRoute();
        let route_name = computed(() => route.name);
        return{
            route_name,
        }
    }
}

</script>


<style scoped>
.information-photo{
    width: 100%;
}
.information-username{
    font-size: large;
    text-align: center;
    font-weight: 700;
}
.information-scores{
    text-align: center;
}
.information-rating{
    text-align: center;
}
</style>