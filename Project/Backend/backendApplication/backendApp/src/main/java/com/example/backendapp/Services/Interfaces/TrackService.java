package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.Entities.TrackWithUserRating;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.Collection;

public interface TrackService {
    Collection<CommentDto> getCommentsByTrackId(Long Id);

    Blob getTrackFileById(Long id);

    String getTrackFileNameById(Long id);

    Collection<TrackWithUserRating> getTracksForUser(Long page, Long number, String searchBy, String searchValue, String order, Long minRate, Long maxRate);

    @Transactional(readOnly = true)
    Long getTracksCount(String searchBy, String searchValue, Long minRate, Long maxRate);

    TrackWithUserRating setTrackRating(Long id, Long rating);
}
