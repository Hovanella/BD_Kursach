package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.TrackDto;
import com.example.backendapp.Repositories.PlaylistRepository;
import com.example.backendapp.Repositories.TrackRepository;
import com.example.backendapp.Services.Interfaces.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaylistServiceImpl(TrackRepository trackRepository, final PlaylistRepository playlistRepository, final ModelMapper modelMapper) {
        this.trackRepository = trackRepository;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<TrackDto> getPlaylistTracksById(final Long id) {

        return playlistRepository.findById(id).get().getTracks().stream().map(track -> {
            var newTrack = this.modelMapper.map(track, TrackDto.class);
            newTrack.setAuthorName(track.getAuthor().getName());
            newTrack.setGenreName(track.getGenre().getName());
            newTrack.setTrackFileId(track.getTrackFile().getId());
            return newTrack;
        }).toList();

    }


}
