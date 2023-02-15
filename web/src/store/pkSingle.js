export default ({
    state: {
        score: 0,
    },
    getters: {
    },
    mutations: {
        addScore(state, score){
            state.score += score;
        },
        setScore(state, score){
            state.score = score;  
        }
    },
    actions: {
    },
    modules: {
    }
  })
  