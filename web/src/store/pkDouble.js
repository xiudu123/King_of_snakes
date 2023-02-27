export default ({
    state: {
        socket: "",
        opponent_photo: "",
        opponent_username: "",
        status: "matching",
    },
    getters: {
        
    },
    mutations: {
        updateSocket(state, socket){
            state.socket = socket;
        },
        updateOpponent(state, opponent){
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status){
            state.status = status;
        }
    },
    actions: {
    },
    modules: {
    }
  })
  