package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.Entities.TrackWithUserRating;

import java.sql.SQLException;
import java.util.Collection;


public interface PlaylistService {
    Collection<TrackWithUserRating> getPlaylistTracksById(Long id) throws SQLException;


    Collection<PlaylistDTO> getAllPlaylists() throws SQLException;

    TrackWithUserRating deleteTrackFromPlaylist(Long id, Long trackId) throws SQLException;

    TrackWithUserRating addTrackToPlaylist(Long id, Long trackId) throws SQLException;

    PlaylistDTO createPlaylist(String name) throws SQLException;
}
