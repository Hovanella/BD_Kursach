import axios from "axios";
import authHeader from "./Headers/authHeader"

const API_URL = "http://localhost:8089/users/";

const getPublicContent = () => {
    return axios.get(API_URL + "all" , { headers: authHeader() });
};

const getLogins = ()=>{
    return axios.get(API_URL + "logins");
}

const UserService = {
    getPublicContent,
    getAllLogins: getLogins
};
export default UserService;
