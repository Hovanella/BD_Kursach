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

const login = async (login, password) => {

    try {
        const data = await axios.post(API_URL + "login", {
            login,
            password,
        });
        localStorage.setItem("token", JSON.stringify(data.data));
        return true;
    } catch (e) {
        alert(e);
        return false;
    }

};

const logout = () => {
    localStorage.removeItem("token");
};

const isAdmin =async () => {
    return await axios.get(`${API_URL}is-admin`, {headers: authHeader()})
}


const AuthService = {
    register,
    login,
    logout,
    isAdmin
};

export default AuthService;