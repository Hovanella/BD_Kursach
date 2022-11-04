package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.TrackDto;

import java.util.Collection;

public interface PlaylistService {
    Collection<TrackDto> getPlaylistTracksById(Long id);


}
