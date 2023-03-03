export default ({
    state: {
        socket: "",
        opponent_photo: "",
        opponent_username: "",
        status: "matching",
        game_map: null,
        map_rows: 0,
        map_cols: 0,
        gameObjectDouble: null,
        loser: "none",
        a_id: 0,
        b_id: 0,

    },
    getters: {
        
    },
    mutations: {
        updateSocketDouble(state, socket){
            state.socket = socket;
        },
        updateOpponent(state, opponent){
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status){
            state.status = status;
        },
        updateGame(state, game){
            state.game_map = game.game_map;
            state.a_id = game.a_id;
            state.b_id = game.b_id;
            state.map_cols = game.cols;
            state.map_rows = game.rows;
        },
        updateGameObjectDouble(state, gameObjectDouble){
            state.gameObjectDouble = gameObjectDouble;
        },
        updateLoser(state, loser){
            state.loser = loser;
        },
    },
    actions: {
    },
    modules: {
    }
  })
  