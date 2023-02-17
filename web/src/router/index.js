import { createRouter, createWebHistory } from 'vue-router'
import HomeIndexView from "@/views/home/HomeIndexView"
import PkIndexView from "@/views/pk/PkIndexView"
import RanklistIndexView from "@/views/ranklist/RanklistIndexView"
import RecordIndexView from "@/views/record/RecordIndexView"
import UserSpaceIndexView from "@/views/user/space/UserSpaceIndexView"
import NotFound from "@/views/error/NotFound"
import PkModeSingleView from "@/views/pk/PkModeSingleView"
import PkModeDoubleView from "@/views/pk/PkModeDoubleView"
import UserLoginView from "@/views/user/account/UserLoginView"
import UserRegisterView from "@/views/user/account/UserRegisterView"
import UserSpacePkSingleView from "@/views/user/space/UserSpacePkSingleView"
import UserSpacePkDoubleView from "@/views/user/space/UserSpacePkDoubleView"

import store from "@/store/index"
const routes = [
  {
      path: "/",
      name: "home",
      redirect: "/home/",
      meta: {
        requestAuth: false,
      }
  },
  {
      path: "/home/",
      name: "home_index",
      component: HomeIndexView,
      meta: {
        requestAuth: false,
      }
  },
  {
      path: "/pk/",
      name: "pk_index",
      component: PkIndexView,
      meta: {
        requestAuth: true,
      }
  },
  {
      path: "/pk/single/",
      name: "pk_single",
      component: PkModeSingleView,
      meta: {
        requestAuth: true,
      }
  },
  {
      path: "/pk/double/",
      name: "pk_double",
      component: PkModeDoubleView,
      meta: {
        requestAuth: true,
      }
  },
  {
      path: "/ranklist/",
      name: "ranklist_index",
      component: RanklistIndexView,
      meta: {
        requestAuth: true,
      }
  },
  {
      path: "/record/",
      name: "record_index",
      component: RecordIndexView,
      meta: {
        requestAuth: true,
      }
  },
  {
    path: "/user/space/",
    name: "user_index",
    component: UserSpaceIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
      path: "/user/space/space/",
      name: "user_space_space",
      component: UserSpaceIndexView,
      meta: {
        requestAuth: true,
      }
  },
  {
    path: "/user/space/pksingle/",
    name: "user_space_pksingle",
    component: UserSpacePkSingleView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/space/pkdouble/",
    name: "user_space_pkdouble",
    component: UserSpacePkDoubleView,
    meta: {
      requestAuth: true,
    }
  },
  {
      path: "/user/login/",
      name: "user_login",
      component: UserLoginView,
      meta: {
        requestAuth: false,
      }
  },
  {
      path: "/user/register/",
      name: "user_register",
      component: UserRegisterView,
      meta: {
        requestAuth: false,
      }
  },
  {
      path: "/404/",
      name: "404",
      component: NotFound,
      meta: {
        requestAuth: false,
      }
  },
  {
      path: "/:catAll(.*)",
      redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
    if(!store.state.user.is_login && localStorage.getItem("jwt_token")){
        store.commit("updateToken", localStorage.getItem("jwt_token"));
        store.dispatch("updateInfo", {
            success(){},
            error(){}
        });
        store.commit("updatePulling", false);
        next();
    }
    else if(to.meta.requestAuth && !store.state.user.is_login){
        store.commit("update_view", to.name);
        store.commit("updatePulling", false);
        next({name: "user_login"});
    }else {
      store.commit("updatePulling", false);
      next();
    }
})

export default router
