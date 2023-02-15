import $ from "jquery"
export default ({
    state: {
        is_login: false,
        id: "",
        username: "",
        photo: "",
        token: "",
        rating: 0,
        scores: 0,
        pulling: true,
    },
    getters: {
    },
    mutations: {
        updateUser(state, user){
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.rating = parseInt(user.rating);
            state.scores = parseInt(user.scores);
            state.is_login = user.is_login;

        },
        updateToken(state, token){
            state.token = token;
        },
        logout(state){
            state.id = "",
            state.username = "",
            state.photo = "",
            state.rating = 0,
            state.scores = 0;
            state.is_login = false;
        },
        updatePulling(state, pulling){
            state.pulling = pulling;
        }
    },
    actions: {
        login(content, data){
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/login/",
                type: "post",
                data: {
                  username: data.username,
                  password: data.password,
                },
                success(resp){
                    if(resp.message === "success"){
                        localStorage.setItem("jwt_token", resp.token);
                        content.commit("updateToken", resp.token);
                        data.success(resp);
                    }else data.error(resp);
                },
                error(resp){
                  data.error(resp);
                }
              });
        },

        updateInfo(content, data){
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + content.state.token,
                },
                success(resp){
                    if(resp.message === "success"){
                        content.commit("updateUser", {
                            ...resp,
                            is_login: true,
                        })
                        data.success(resp);
                    }else data.error(resp);
                },
                error(resp){
                    data.error(resp);
                }
            })
        },

        logout(content){
            localStorage.removeItem("jwt_token");
            content.commit("logout");
            location.reload() //刷新页面;
        },
    },
    modules: {
    }
  })
  