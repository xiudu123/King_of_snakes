import { createStore } from 'vuex'
import ModulePkmode from './pkMode'
import ModulePkSingle from './pkSingle'
import ModuleUser from "./User"
import ModuleViews from "./views"
export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    pkMode: ModulePkmode,
    pkSingle: ModulePkSingle,
    user: ModuleUser,
    views: ModuleViews,
  }
})
