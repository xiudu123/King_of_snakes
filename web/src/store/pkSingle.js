export default ({
    state: {
        score: 0,
        socket: "",
        status: "await",
        gamme_map: null,
    },
    getters: {
    },
    mutations: {
        updateSocketSingle(state, socket){
            state.socket = socket;
        },
        addScore(state, score){
            state.score += score;
        },
        setScore(state, score){
            state.score = score;  
        },
        updateGamemapSingle(state, game_map){
            state.game_map = game_map;
        },
        updateStatusSingle(state, status){
            state.status = status;
        }
    },
    actions: {
    },
    modules: {
    }
  })
  