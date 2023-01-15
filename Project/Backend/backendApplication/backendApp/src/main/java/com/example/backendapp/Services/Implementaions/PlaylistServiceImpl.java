package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Repositories.PlaylistRepository;
import com.example.backendapp.Repositories.TrackRepository;
import com.example.backendapp.Repositories.TrackWithUserRatingRepository;
import com.example.backendapp.Repositories.UserRepository;
import com.example.backendapp.Services.Interfaces.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final TrackRepository trackRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TrackWithUserRatingRepository trackWithUserRatingRepository;

    @Autowired
    public PlaylistServiceImpl(TrackRepository trackRepository, final PlaylistRepository playlistRepository, final ModelMapper modelMapper, UserRepository userRepository, TrackWithUserRatingRepository trackWithUserRatingRepository) {
        this.trackRepository = trackRepository;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.trackWithUserRatingRepository = trackWithUserRatingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<TrackWithUserRating> getPlaylistTracksById(final Long id) throws SQLException {

        var playlist = playlistRepository.getPlaylistById(id);
        if (playlist == null) {
            return null;
        }
        var playlistId = playlist.getId();

        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return trackWithUserRatingRepository.getPlaylistTracks(playlistId, userId);

    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PlaylistDTO> getAllPlaylists() throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        var playlists = playlistRepository.getAllUserPlaylists(userId);

        var playlistsDto = playlists.stream().map(playlist -> {
            var newPlaylist = modelMapper.map(playlist, PlaylistDTO.class);

            try {
                var author = userRepository.findUserById(playlist.getUserId());
                newPlaylist.setAuthorName(author.getLogin());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return newPlaylist;
        }).toList();

        return playlistsDto;
    }

    @Override
    @Transactional(readOnly = true)
    public TrackWithUserRating deleteTrackFromPlaylist(Long id, Long trackId) throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return trackWithUserRatingRepository.deleteTrackFromPlaylist(id, trackId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public TrackWithUserRating addTrackToPlaylist(Long id, Long trackId) throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return trackWithUserRatingRepository.addTrackToPlaylist(id, trackId, userId);
    }

    @Override
    public PlaylistDTO createPlaylist(String name) throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();
        var playlist = playlistRepository.createPlaylist(name, userId);
        var newPlaylist = modelMapper.map(playlist, PlaylistDTO.class);
        try {
            var author = userRepository.findUserById(playlist.getUserId());
            newPlaylist.setAuthorName(author.getLogin());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newPlaylist;
    }

    @Override
    public void deletePlaylist(Long id) throws SQLException {
        playlistRepository.deletePlaylist(id);
    }


}
