package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.DTO.TrackDto;
import com.example.backendapp.Entities.Author;
import com.example.backendapp.Entities.Genre;

import java.util.Collection;

public interface TrackService {
    Collection<CommentDto> getCommentsByTrackId(Long Id);

    Collection<Genre> getTrackGenres();

    Collection<Author> getTrackAuthors();

    Collection<TrackDto> getTracks(Long page, Long number);

    byte[] getTrackFileById(Long id);

    String getTrackFileNameById(Long id);
}
