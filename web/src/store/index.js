import { createStore } from 'vuex'
import ModulePkmode from './pkMode'
import ModulePkSingle from './pkSingle'
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
  }
})
