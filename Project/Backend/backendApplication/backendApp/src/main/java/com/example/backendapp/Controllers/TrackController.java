package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.Entities.Author;
import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Services.Interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

@Controller
@CrossOrigin("*")
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    @Value("${upload-path}")
    private String uploadPath;

    @Autowired
    public TrackController(final TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDto>> getTrackCommentsById(@PathVariable final Long id) {
        final var comments = this.trackService.getCommentsByTrackId(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Genre>> getGenres() {
        final var genres = this.trackService.getTrackGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Author>> getAuthors() {
        final var authors = this.trackService.getTrackAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "", params = {"number", "page"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTracks(@RequestParam final Long number, @RequestParam final Long page) {

        try {
            return new ResponseEntity<>(trackService.getTracks(page, number), HttpStatus.OK);
        } catch (final Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/{id}/file", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTrackFileById(@PathVariable final Long id) throws IOException {

        byte[] trackFile = this.trackService.getTrackFileById(id);
        var fileName = this.trackService.getTrackFileNameById(id);
        var newFile = new File(uploadPath + fileName + ".mp3");
        Files.write(newFile.toPath(), trackFile);

        return new ResponseEntity<>(newFile.getAbsolutePath().substring(newFile.getAbsolutePath().lastIndexOf('\\') + 1), HttpStatus.OK);

    }

}
