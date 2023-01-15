package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.CreatedTrack;
import com.example.backendapp.Entities.TrackWithUserRating;
import com.example.backendapp.Services.Interfaces.TrackService;
import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONArray;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

@Controller
@CrossOrigin("*")
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;

    @Value("${export-path}")
    private String uploadPath;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }


    @GetMapping(value = "/{id}/file")
    public void getTrackFileById(@PathVariable Long id, HttpServletResponse response) throws IOException, SQLException, InterruptedException {
        Blob trackBlob = trackService.getTrackFileById(id);
        var trackFile = trackBlob.getBytes(1, (int) trackBlob.length());
        response.setContentType("audio/mpeg");
        response.setContentLength(trackFile.length);
        response.setHeader("Content-Disposition", "attachment; filename=" + trackService.getTrackFileNameById(id) + ".mp3");
        InputStream inputStream = new ByteArrayInputStream(trackFile);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping(value = "tracks-with-ratings", params = {"number", "page", "search-by", "search-value", "order", "min-rate", "max-rate"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    public ResponseEntity<Collection<TrackWithUserRating>> getTracks(@RequestParam Long number, @RequestParam Long page, @RequestParam(name = "search-by") String searchBy, @RequestParam(name = "search-value") String searchValue, @RequestParam("order") String order, @RequestParam("min-rate") Long minRate, @RequestParam("max-rate") Long maxRate) throws SQLException {

        return new ResponseEntity<>(this.trackService.getTracksForUser(page, number, searchBy, searchValue, order, minRate, maxRate), HttpStatus.OK);

    }

    @GetMapping(value = "count", params = {"search-by", "search-value", "min-rate", "max-rate"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Long> getTracksCount(@RequestParam("search-by") String searchBy, @RequestParam("search-value") String searchValue, @RequestParam("min-rate") Long minRate, @RequestParam("max-rate") Long maxRate) throws SQLException {
        var count = this.trackService.getTracksCount(searchBy, searchValue, minRate, maxRate);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/set-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrackWithUserRating> setTrackRating(@PathVariable Long id, @RequestBody JsonNode requestBody) throws SQLException {

        var trackWithUserRating = trackService.setTrackRating(id, requestBody.get("rating").asLong());

        return new ResponseEntity<>(trackWithUserRating, HttpStatus.OK);
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> uploadTrackFile(@RequestBody MultipartFile file) throws IOException, SQLException {
        return new ResponseEntity<>(trackService.uploadTrackFile(file), HttpStatus.OK);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedTrack> createTrack(@RequestBody JsonNode requestBody) throws SQLException {
        return new ResponseEntity<>(trackService.createTrack(requestBody), HttpStatus.OK);
    }

    @GetMapping(value = "/export-tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONArray> exportTracks() throws SQLException {
        var path = uploadPath + "tracks.json";
        var tracks = trackService.exportTracks();

        var file = new File(path);

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            for (var track : tracks) {
                out.println(track);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(trackService.exportTracks(), HttpStatus.OK);
    }

    @PostMapping(value = "/import-tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> importTracks() throws SQLException {
        var path = uploadPath + "tracks.json";
        //read json file data to String
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(trackService.importTracks(contentBuilder.toString()), HttpStatus.OK);

    }

}
