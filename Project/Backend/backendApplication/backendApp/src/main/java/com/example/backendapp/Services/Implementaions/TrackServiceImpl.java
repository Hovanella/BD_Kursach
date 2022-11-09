package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.DTO.TrackDto;
import com.example.backendapp.Entities.Author;
import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Repositories.AuthorRepository;
import com.example.backendapp.Repositories.CommentRepository;
import com.example.backendapp.Repositories.GenreRepository;
import com.example.backendapp.Repositories.TrackRepository;
import com.example.backendapp.Services.Interfaces.TrackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TrackServiceImpl implements TrackService {

    private final CommentRepository commentRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(final CommentRepository commentRepository, final GenreRepository genreRepository, final ModelMapper modelMapper, final AuthorRepository authorRepository, TrackRepository trackRepository) {
        this.commentRepository = commentRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.trackRepository = trackRepository;
    }

    @Override
    public Collection<CommentDto> getCommentsByTrackId(final Long Id) {

        final var comments = this.commentRepository.getCommentsByTrackId(Id).stream().toList();

        return comments.stream().map(comment -> {
            final var newComment = this.modelMapper.map(comment, CommentDto.class);
            newComment.setAuthorName(comment.getUser().getLogin());
            return newComment;
        }).toList();
    }

    @Override
    public Collection<Genre> getTrackGenres() {
        return this.genreRepository.findAll();
    }

    @Override
    public Collection<Author> getTrackAuthors() {
        return this.authorRepository.findAll();
    }

    @Override
    public Collection<TrackDto> getTracks(Long page, Long number) {

        var trackCount = this.trackRepository.count();
        var pageCount = trackCount / number == 0 ? trackCount / number : trackCount / number + 1;

        if (page > pageCount) {
            throw new IllegalArgumentException("Page number is greater than page count");
        }


        return this.trackRepository.findPageable(page * number, number).stream().map(track -> {
            var newTrack = this.modelMapper.map(track, TrackDto.class);
            newTrack.setAuthorName(track.getAuthor().getName());
            newTrack.setGenreName(track.getGenre().getName());
            return newTrack;
        }).toList();

    }

    @Override
    public byte[] getTrackFileById(Long id) {
        return this.trackRepository.findById(id).get().getTrackFile().getTrackFile();
    }

    @Override
    public String getTrackFileNameById(Long id) {
        return this.trackRepository.findById(id).get().getName();
    }

    @Override
    public Collection<TrackDto> getTracksForUser(Long page, Long number) {
        var currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (currentUser == null)
            throw new BadCredentialsException("Not authorized");
        var allTracks = new ArrayList<TrackDto>();
      /*

        trackRepository.findAllForUser().forEach(track -> allTracks.add(modelMapper.map(track, TrackDto.class)));

        var userRatings = new ArrayList<RatingDto>();
        ratingRepository.findByUser(currentUser).forEach(rating -> userRatings.add(modelMapper.map(rating, RatingDto.class)));

        for (var track : allTracks) {
            for (var rating : userRatings) {
                if (track.getId().equals(rating.getTrack().getId())) {
                    track.setRating(rating.getMark());
                }
            }
        }*/

        return allTracks;
    }

}
