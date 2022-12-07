package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.CreatedTrack;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONArray;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

public interface TrackService {


    Blob getTrackFileById(Long id) throws SQLException;

    String getTrackFileNameById(Long id) throws SQLException;

    Collection<TrackWithUserRating> getTracksForUser(Long page, Long number, String searchBy, String searchValue, String order, Long minRate, Long maxRate) throws SQLException;

    @Transactional(readOnly = true)
    Long getTracksCount(String searchBy, String searchValue, Long minRate, Long maxRate) throws SQLException;

    TrackWithUserRating setTrackRating(Long id, Long rating) throws SQLException;

    Long uploadTrackFile(MultipartFile fileBytes) throws IOException, SQLException;

    CreatedTrack createTrack(JsonNode requestBody) throws SQLException;

    JSONArray exportTracks() throws SQLException;
}
