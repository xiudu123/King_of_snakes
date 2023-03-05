<template>
    <div class="container" >
        <div class="row">
            <div class="col-12 ">
                <ContentField>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/record/single/" :class="route_name === 'record_index' || route_name === 'record_single' ? 'active': '' ">单人模式</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/record/double/" :class="route_name === 'record_double' ? 'active': '' ">双人对战</a>
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
</style>