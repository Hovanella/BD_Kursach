import axios from "axios";
import authHeader from "./Headers/authHeader";


const API_URL = "http://localhost:8089/authors/";

const AddAuthor = (name) => {
    return axios.post(`${API_URL}AddAuthor`, { name }, { headers: authHeader() });
}
const GetAuthorByName = (name) => {
    return axios.get(`${API_URL}GetAuthor/${name}`, { headers: authHeader() });
}
const getAllAuthors = () => {
    return axios.get(`${API_URL}GetAllAuthors`, { headers: authHeader() });
}

const AuthorService = {
    AddAuthor,
    GetAuthorByName,
    getAllAuthors

};
export default AuthorService ;
