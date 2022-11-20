package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.Entities.Author;
import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Services.Interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

@Controller
@CrossOrigin("*")
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    @Value("${upload-path}")
    private String uploadPath;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDto>> getTrackCommentsById(@PathVariable Long id) {
        var comments = trackService.getCommentsByTrackId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Genre>> getGenres() {
        var genres = trackService.getTrackGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Author>> getAuthors() {
        var authors = trackService.getTrackAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }


    @PostMapping(value = "/{id}/file", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @CrossOrigin("*")
    public ResponseEntity<String> getTrackFileById(@PathVariable Long id) throws IOException, SQLException, InterruptedException {

        Blob trackBlob = trackService.getTrackFileById(id);
        var trackFile = trackBlob.getBytes(1, (int) trackBlob.length());
        final var fileName = trackService.getTrackFileNameById(id);

        
        final var newFile = new File(this.uploadPath + fileName + ".mp3");
        if (newFile.exists()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        synchronized (this) {
            try {
                Files.write(newFile.toPath(), trackFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ResponseEntity<>(fileName, HttpStatus.OK);


    }

    @GetMapping(value = "tracks-with-ratings", params = {"number", "page", "search-by", "search-value", "order", "min-rate", "max-rate"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<Collection<TrackWithUserRating>> getTracks(@RequestParam Long number, @RequestParam Long page, @RequestParam(name = "search-by") String searchBy, @RequestParam(name = "search-value") String searchValue, @RequestParam("order") String order, @RequestParam("min-rate") Long minRate, @RequestParam("max-rate") Long maxRate) {

        return new ResponseEntity<>(this.trackService.getTracksForUser(page, number, searchBy, searchValue, order, minRate, maxRate), HttpStatus.OK);

    }

    @GetMapping(value = "count", params = {"search-by", "search-value", "min-rate", "max-rate"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Long> getTracksCount(@RequestParam("search-by") String searchBy, @RequestParam("search-value") String searchValue, @RequestParam("min-rate") Long minRate, @RequestParam("max-rate") Long maxRate) {
        var count = this.trackService.getTracksCount(searchBy, searchValue, minRate, maxRate);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


}
