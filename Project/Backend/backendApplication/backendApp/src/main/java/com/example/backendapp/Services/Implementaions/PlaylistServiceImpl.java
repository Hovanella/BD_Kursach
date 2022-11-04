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
    public PlaylistServiceImpl(final TrackRepository trackRepository, PlaylistRepository playlistRepository, ModelMapper modelMapper) {
        this.trackRepository = trackRepository;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<TrackDto> getPlaylistTracksById(Long id) {

        return this.playlistRepository.findById(id).get().getTracks().stream().map(track -> {
            final var newTrack = modelMapper.map(track, TrackDto.class);
            newTrack.setAuthorName(track.getAuthor().getName());
            newTrack.setGenreName(track.getGenre().getName());
            newTrack.setTrackFileId(track.getTrackFile().getId());
            return newTrack;
        }).toList();

    }


}
