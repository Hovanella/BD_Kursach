package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.CreatedTrack;
import com.example.backendapp.Entities.Rating;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Repositories.*;
import com.example.backendapp.Services.Interfaces.TrackService;
import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;


@Service
public class TrackServiceImpl implements TrackService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final TrackRepository trackRepository;
    private final TrackWithUserRatingRepository trackWithUserRatingRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final JsonRepository jsonRepository;

    @Autowired
    public TrackServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper, AuthorRepository authorRepository, final TrackRepository trackRepository, final TrackWithUserRatingRepository trackWithUserRatingRepository, final UserRepository userRepository, RatingRepository ratingRepository, JsonRepository jsonRepository) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.trackRepository = trackRepository;
        this.trackWithUserRatingRepository = trackWithUserRatingRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.jsonRepository = jsonRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Blob getTrackFileById(final Long id) throws SQLException {
        return trackRepository.getTrackFile(id);
    }

    @Override
    public String getTrackFileNameById(final Long id) throws SQLException {
        return trackRepository.findTrackById(id).getName();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<TrackWithUserRating> getTracksForUser(final Long page, final Long number, final String searchBy, final String searchValue, final String order, final Long minRate, final Long maxRate) throws SQLException {


        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return this.trackWithUserRatingRepository.findTracksForUser(userId, page * number, number, searchBy, searchValue, order, minRate, maxRate);
    }


    @Override
    @Transactional(readOnly = true)
    public Long getTracksCount(String searchBy, String searchValue, Long minRate, Long maxRate) throws SQLException {

        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return trackRepository.getTrackCount(userId, searchBy, searchValue, minRate, maxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public TrackWithUserRating setTrackRating(Long TrackId, Long rating) throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        Rating ratingForTrack = ratingRepository.getRatingForTrackFromUser(userId, TrackId);

        if (null == ratingForTrack)
            return trackWithUserRatingRepository.setTrackRating(TrackId, userId, rating);
        else
            return trackWithUserRatingRepository.updateTrackRating(ratingForTrack.getId(), rating);

    }

    @Override
    @Transactional(readOnly = true)
    public Long uploadTrackFile(MultipartFile file) throws IOException, SQLException {
        var fileBytes = file.getBytes();
        Blob blob = new SerialBlob(fileBytes);
        return trackRepository.uploadTrackFile(fileBytes);
    }

    @Override
    @Transactional(readOnly = true)
    public CreatedTrack createTrack(JsonNode requestBody) throws SQLException {
        var name = requestBody.get("name").asText();
        var genreId = requestBody.get("genreId").asLong();
        var authorId = requestBody.get("authorId").asLong();
        var trackFileId = requestBody.get("trackFileId").asLong();

        var track = trackRepository.createTrack(name, genreId, authorId, trackFileId);
        return modelMapper.map(track, CreatedTrack.class);
    }

    @Override
    public JSONArray exportTracks() throws SQLException {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return jsonRepository.ExportTracksForUser(userId);
    }

}

