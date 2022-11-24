package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.CommentDto;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Services.Interfaces.TrackService;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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


    //
    @GetMapping(value = "/{id}/file")
    @CrossOrigin("*")
    public void getTrackFileById(@PathVariable Long id, HttpServletResponse response) throws IOException, SQLException, InterruptedException {


        /*final var fileName = trackService.getTrackFileNameById(id);
        final var newFile = new File(uploadPath + fileName + ".mp3");
        Files.write(newFile.toPath(), trackFile);*/

        Blob trackBlob = trackService.getTrackFileById(id);
        var trackFile = trackBlob.getBytes(1, (int) trackBlob.length());
        response.setContentType("audio/mpeg");
        response.setContentLength(trackFile.length);
        response.setHeader("Content-Disposition", "attachment; filename=" + trackService.getTrackFileNameById(id) + ".mp3");
        InputStream inputStream = new ByteArrayInputStream(trackFile);
        IOUtils.copy(inputStream, response.getOutputStream());
        /*   response.flushBuffer();*/
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

    @PostMapping(value = "/{id}/set-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrackWithUserRating> setTrackRating(@PathVariable Long id, @RequestBody JsonNode requestBody) {
        var trackWithUserRating = trackService.setTrackRating(id, requestBody.get("rating").asLong());
        return new ResponseEntity<>(trackWithUserRating, HttpStatus.OK);
    }

}
