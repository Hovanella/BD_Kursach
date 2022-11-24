import axios from "axios";
import authHeader from "./Headers/authHeader";

const API_URL = "http://localhost:8089/users/";

const register = (login, password, email) => {
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
        }).catch((error) => {
            alert(error);
        });
};

const logout = () => {
    localStorage.removeItem("token");
};

const isAdmin = () => {
    return axios.get(`${API_URL}is-admin`, {headers: authHeader()})
}


const AuthService = {
    register,
    login,
    logout,
    isAdmin
};

export default AuthService;