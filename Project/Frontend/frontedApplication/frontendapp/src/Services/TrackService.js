import axios from "axios";
import authHeader from "./Headers/authHeader";


const API_URL = "http://localhost:8089/tracks/";

const getTracksPage = async (page = 0, number, searchBy = 'name', searchValue = '', order = 'ascending', minRate = 0, maxRate = 10) => {
    console.log("page" + page);
    const response = await axios.get(`${API_URL}tracks-with-ratings?page=${page}&number=${number}&search-by=${searchBy}&search-value=${searchValue}&order=${order}&min-rate=${minRate}&max-rate=${maxRate}`, {headers: authHeader()});
    return response.data;
};

const postOrUpdateRating = (mark, trackId) => {
    const login = JSON.parse(localStorage.getItem("user")).login;
    return axios.post(`${API_URL}AddOrUpdateRating`, {mark, login, trackId}, {headers: authHeader()});
}

const postTrack = (genreId, authorId, name, path) => {
    return axios.post(`${API_URL}AddTrack`, {genreId, authorId, name, path}, {headers: authHeader()});
}

const CopyTrackInAudio = async (trackId) => {
    const response =  await axios.post(`${API_URL}${trackId}/file`, {}, {headers:authHeader()});
    console.log(response.data);
    return response.data;
}

const getTrackCount = async (searchBy = 'name', searchValue = '', minRate = 0, maxRate = 10) => {
    const response = await axios.get(`${API_URL}count?search-by=${searchBy}&search-value=${searchValue}&min-rate=${minRate}&max-rate=${maxRate}`, {headers: authHeader()});
    return response.data;
}

const TrackService = {
    getTracksPage,
    postOrUpdateRating,
    postTrack,
    CopyTrackInAudio,
    getTrackCount
};
export default TrackService;
