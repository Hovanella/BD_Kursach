package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.TrackDto;
import com.example.backendapp.Services.Interfaces.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.sql.DataSourceDefinition;
import java.util.Collection;

@Controller
@CrossOrigin("*")
@RequestMapping("/playlists/")
@DataSourceDefinition(name = "DBAdminDataSource", className = "JpaConfig")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping(value = "{id}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TrackDto>> getPlaylistTracksById(@PathVariable final Long id) {

        return new ResponseEntity<>(this.playlistService.getPlaylistTracksById(id), HttpStatus.OK);
    }


}
