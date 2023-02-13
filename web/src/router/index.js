import { createRouter, createWebHistory } from 'vue-router'
import HomeIndexView from "@/views/home/HomeIndexView"
import PkIndexView from "@/views/pk/PkIndexView"
import RanklistIndexView from "@/views/ranklist/RanklistIndexView"
import RecordIndexView from "@/views/record/RecordIndexView"
import UserIndexView from "@/views/user/UserIndexView"
import NotFound from "@/views/error/NotFound"
import PkModeSingleView from "@/views/pk/PkModeSingleView"
import PkModeDoubleView from "@/views/pk/PkModeDoubleView"
const routes = [
  {
      path: "/",
      name: "home",
      redirect: "/home/",
  },
  {
      path: "/home/",
      name: "home_index",
      component: HomeIndexView,
  },
  {
      path: "/pk/",
      name: "pk_index",
      component: PkIndexView,
  },
  {
      path: "/pk/single/",
      name: "pk_single",
      component: PkModeSingleView,
  },
  {
      path: "/pk/double/",
      name: "pk_double",
      component: PkModeDoubleView,
  },
  {
      path: "/ranklist/",
      name: "ranklist_index",
      component: RanklistIndexView,
  },
  {
      path: "/record/",
      name: "record_index",
      component: RecordIndexView,
  },
  {
      path: "/user/",
      name: "user_index",
      component: UserIndexView,
  },
  {
      path: "/404/",
      name: "404",
      component: NotFound,
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

export default router
