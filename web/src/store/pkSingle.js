export default ({
    state: {
        score: 0,
        socket: "",
        status: "await",
        gamme_map: null,
        sx: 0,
        sy: 0,
        food_x: 0,
        food_y: 0,
        direction: -1,
        increasing: false,
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
        },
        updateLocation(state, location){
            state.sx = location.sx;
            state.sy = location.sy;
        },
        updateFood(state, food){
            state.food_x = food.food_x;
            state.food_y = food.food_y;
        },
        updateDirection(state, direction){
            state.direction = direction;
        },
        updateIncreasing(state, increasing){
            state.increasing = increasing;
        },
    },
    actions: {
    },
    modules: {
    }
  })
  