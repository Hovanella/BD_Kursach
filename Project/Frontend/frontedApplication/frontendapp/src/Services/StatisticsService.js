import axios from "axios";
import authHeader from "./Headers/authHeader";


const API_URL = "http://localhost:8089/statistics/";

const getTracksWithLargestAverageRating = async () => {
    return await axios.get(`${API_URL}tracks-with-largest-largest-average-rating`, {
        headers: authHeader()
    });
}

const getTracksWithLargestNumberOfRatings = async () => {
    return await axios.get(`${API_URL}tracks-with-largest-number-of-ratings`, {
        headers: authHeader()
    });
}

const getGenresWithLargestNumberOfTracks = async () => {
    return await axios.get(`${API_URL}genres-with-largest-number-of-tracks`, {
        headers: authHeader()
    });
}

const getGenresWithLargestNumberOfRatings = async () => {
    return await axios.get(`${API_URL}genres-with-largest-number-of-ratings`, {
        headers: authHeader()
    });
}

const getAuthorsWithLargestNumberOfTracks = async () => {
    return await axios.get(`${API_URL}authors-with-largest-number-of-tracks`, {
        headers: authHeader()
    });
}

const getAuthorsWithLargestNumberOfRatings = async () => {
    return await axios.get(`${API_URL}authors-with-largest-number-of-ratings`, {
        headers: authHeader()
    });
}

const getUsersWithLargestNumberOfRatings = async () => {
    return await axios.get(`${API_URL}users-with-largest-number-of-ratings`, {
        headers: authHeader()
    });
}


const StatisticsService = {
    getTracksWithLargestAverageRating,
    getTracksWithLargestNumberOfRatings,
    getGenresWithLargestNumberOfTracks,
    getGenresWithLargestNumberOfRatings,
    getAuthorsWithLargestNumberOfTracks,
    getAuthorsWithLargestNumberOfRatings,
    getUsersWithLargestNumberOfRatings

};
export default StatisticsService;
