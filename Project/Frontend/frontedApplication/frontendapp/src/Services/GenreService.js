import axios from "axios";
import authHeader from "./Headers/authHeader";


const API_URL = "http://localhost:8089/genres/";

const AddGenre = (name) => {
    return axios.post(`${API_URL}`, {name}, {headers: authHeader()});
}

const GetGenreByName = (name) => {
    return axios.get(`${API_URL}GetGenre/${name}`, {
        headers: authHeader()
    });

}

const getAllGenres = () => {
    return axios.get(`${API_URL}`, {headers: authHeader()});
}

const GenreService = {
    AddGenre,
    GetGenreByName,
    getAllGenres
};
export default GenreService;
