import authHeader from "./Headers/authHeader";
import axios from "axios";

const API_URL = "http://localhost:8089/playlists/";

const getPlaylists = async () => {
    const response =  await axios.get(`${API_URL}get-for-user`, {headers: authHeader()});
    return response.data;
}

const getPlaylistTracks = async (id) => {
    if (id===-1) return [];
    const response =  await axios.get(`${API_URL}${id}/tracks`, {headers: authHeader()});
    return response.data;
}

const deleteTrackFromPlaylist = async (trackId,playlistId)=>{
    const response  = await axios.delete(`${API_URL}${playlistId}/tracks/${trackId}`, {headers:authHeader()})
    return response.data;
}

const  addTrackToPlaylist = async (trackId, selectedPlaylist)=>{
    console.log("addTrackToPlaylist",selectedPlaylist,trackId);
    const response = await axios.post(`${API_URL}${selectedPlaylist}/tracks/${trackId}`,{}, {headers:authHeader()})
    console.log(response.data);
    return response.data;
}

const PlaylistsService = {
    getPlaylists,
    getPlaylistTracks,
    deleteTrackFromPlaylist,
    addTrackToPlaylist
};

export default PlaylistsService;