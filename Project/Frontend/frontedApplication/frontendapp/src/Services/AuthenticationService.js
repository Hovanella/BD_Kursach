import axios from "axios";

const API_URL = "http://localhost:8089/users/";

const register = (login, password,email) => {
    return axios.post(API_URL + "register", {
        login,
        password,
        email
    }).then((response) => {
            localStorage.setItem("token", JSON.stringify(response.data));
        return response.data;
    });
};

const login = (login, password) => {
    return axios
        .post(API_URL + "login", {
            login,
            password,
        })
        .then((response) => {
                localStorage.setItem("token", JSON.stringify(response.data));
            return response.isAdmin;
        }).catch((error) => {
            alert(error);
        });
};

const logout = () => {
    localStorage.removeItem("token");
};

const AuthService = {
    register,
    login,
    logout,
};

export default AuthService;