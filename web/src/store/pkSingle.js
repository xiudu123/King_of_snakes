export default ({
    state: {
        score: 0,
        socket: "",
        status: "await",
        gamme_map: null,
        map_rows: 13,
        map_cols: 14,
        sx: 0,
        sy: 0,
        food_x: 0,
        food_y: 0,
        increasing: false,
        gameObjectSingle: null,
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
        updateGamemapSingle(state, map){
            state.game_map = map.game_map;
            state.map_rows = map.rows;
            state.map_cols = map.cols;
        },
        updateStatusSingle(state, status){
            state.status = status;
        },
        updateLocation(state, location){
            state.sx = location.sx;
            state.sy = location.sy;
        },
        updateFood(state, food){
            state.food_x = food.food_x;
            state.food_y = food.food_y;
        },
        updateIncreasing(state, increasing){
            state.increasing = increasing;
        },
        updateGameObjectSingle(state, gameObjectSingle){
            state.gameObjectSingle = gameObjectSingle;
        },
    },
    actions: {
    },
    modules: {
    }
  })
  