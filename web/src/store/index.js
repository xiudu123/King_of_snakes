import { createStore } from 'vuex'
import ModulePkmode from './pkMode'
import ModulePkSingle from './pkSingle'
import ModulePkDouble from './pkDouble'
import ModuleUser from "./user"
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
    pkDouble: ModulePkDouble,
    user: ModuleUser,
    views: ModuleViews,
  }
})
