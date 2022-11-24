package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.Entities.Rating;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Repositories.*;
import com.example.backendapp.Services.Interfaces.TrackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.Collection;


@Service
public class TrackServiceImpl implements TrackService {

    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final TrackRepository trackRepository;
    private final TrackWithUserRatingRepository trackWithUserRatingRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public TrackServiceImpl(CommentRepository commentRepository, GenreRepository genreRepository, ModelMapper modelMapper, AuthorRepository authorRepository, final TrackRepository trackRepository, final TrackWithUserRatingRepository trackWithUserRatingRepository, final UserRepository userRepository, RatingRepository ratingRepository) {
        this.commentRepository = commentRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.trackRepository = trackRepository;
        this.trackWithUserRatingRepository = trackWithUserRatingRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Collection<CommentDto> getCommentsByTrackId(Long Id) {

        var comments = commentRepository.getCommentsByTrackId(Id).stream().toList();

        return comments.stream().map(comment -> {
            var newComment = modelMapper.map(comment, CommentDto.class);
            newComment.setAuthorName(comment.getUser().getLogin());
            return newComment;
        }).toList();
    }


    @Override
    public Blob getTrackFileById(final Long id) {
        return trackRepository.getTrackFile(id);
    }

    @Override
    public String getTrackFileNameById(final Long id) {
        return trackRepository.findById(id).get().getName();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<TrackWithUserRating> getTracksForUser(final Long page, final Long number, final String searchBy, final String searchValue, final String order, final Long minRate, final Long maxRate) {

        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return this.trackWithUserRatingRepository.findTracksForUser(userId, page * number, number, searchBy, searchValue, order, minRate, maxRate);
    }


    @Override
    @Transactional(readOnly = true)
    public Long getTracksCount(String searchBy, String searchValue, Long minRate, Long maxRate) {

        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userId = this.userRepository.findByLogin(currentUserDetails.getUsername()).getId();

        return trackRepository.getTrackCount(userId, searchBy, searchValue, minRate, maxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public TrackWithUserRating setTrackRating(Long TrackId, Long rating) {
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
}

