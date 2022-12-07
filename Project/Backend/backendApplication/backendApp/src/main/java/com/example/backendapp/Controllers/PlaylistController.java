package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Services.Interfaces.PlaylistService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@Controller
@CrossOrigin("*")
@RequestMapping("/playlists/")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(final PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping(value = "{id}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TrackWithUserRating>> getPlaylistTracksById(@PathVariable Long id) throws SQLException {

        return new ResponseEntity<>(playlistService.getPlaylistTracksById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/tracks/{trackId}")
    public ResponseEntity<TrackWithUserRating> deleteTrackFromPlaylist(@PathVariable Long id, @PathVariable Long trackId) throws SQLException {
        var track = playlistService.deleteTrackFromPlaylist(id, trackId);
        return new ResponseEntity<>(track, HttpStatus.OK);
    }

    @PostMapping(value = "{id}/tracks/{trackId}")
    public ResponseEntity<TrackWithUserRating> addTrackToPlaylist(@PathVariable Long id, @PathVariable Long trackId) throws SQLException {
        var track = playlistService.addTrackToPlaylist(id, trackId);
        return new ResponseEntity<>(track, HttpStatus.OK);
    }

    @GetMapping(value = "get-for-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PlaylistDTO>> getAllPlaylists() throws SQLException {
        return new ResponseEntity<>(playlistService.getAllPlaylists(), HttpStatus.OK);
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlaylistDTO> createPlaylist(@RequestBody JsonNode jsonNode) throws SQLException {
        var name = jsonNode.get("name").asText();
        var playlist = playlistService.createPlaylist(name);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }


}
