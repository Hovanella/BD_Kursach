import axios from "axios";
import authHeader from "./Headers/authHeader";


const API_URL = "http://localhost:8089/tracks/";

const getTracksPage = async (page = 0, number, searchBy = 'name', searchValue = '', order = 'ascending', minRate = 0, maxRate = 10) => {
    console.log("page" + page);
    const response = await axios.get(`${API_URL}tracks-with-ratings?page=${page}&number=${number}&search-by=${searchBy}&search-value=${searchValue}&order=${order}&min-rate=${minRate}&max-rate=${maxRate}`, {headers: authHeader()});
    return response.data;
};

const setTrackRating = (rating, trackId) => {
    return axios.post(`${API_URL}${trackId}/set-rating`, {rating}, {headers: authHeader()});
}

const postTrack = (genreId, authorId, name, trackFileId) => {
    console.log("trackFileId " + trackFileId + " genreId " + genreId + " authorId " + authorId + " name " + name);
    return axios.post(`${API_URL}create`, {name, genreId, authorId, trackFileId}, {headers: authHeader()});
}

const postTrackFile = async (formData) => {

    const options = {
        method: "POST",
        body: formData,
        headers: authHeader(),
        accept: "text/plain"
    }
    return await fetch(`${API_URL}files`, options);

}

const getTrackCount = async (searchBy = 'name', searchValue = '', minRate = 0, maxRate = 10) => {
    console.log("searchValue " + searchValue);
    const response = await axios.get(`${API_URL}count?search-by=${searchBy}&search-value=${searchValue}&min-rate=${minRate}&max-rate=${maxRate}`, {headers: authHeader()});
    return response.data;
}

const TrackService = {
    getTracksPage,
    setTrackRating,
    postTrack,
    postTrackFile,
    getTrackCount
};
export default TrackService;
