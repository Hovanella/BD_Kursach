package com.example.backendapp.Controllers;

import com.example.backendapp.Entities.*;
import com.example.backendapp.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/statistics/")
@CrossOrigin("*")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StatisticsController {

    private final TracksWithLargestAverageRatingRepository tracksWithLargestAverageRatingRepository;
    private final TracksWithLargestNumberOfRatingsRepository tracksWithLargestNumberOfRatingsRepository;
    private final UsersWithLargestNumberOfRatingsRepository usersWithLargestNumberOfRatingsRepository;
    private final GenresWithLargestNumberOfTracksRepository genresWithLargestNumberOfTracksRepository;
    private final GenresWithLargestNumberOfRatingsRepository genresWithLargestNumberOfRatingsRepository;
    private final AuthorsWithLargestNumberOfTracksRepository authorsWithLargestNumberOfTracksRepository;
    private final AuthorsWithLargestNumberOfRatingsRepository authorsWithLargestNumberOfRatingsRepository;


    @Autowired
    public StatisticsController(TracksWithLargestAverageRatingRepository tracksWithLargestAverageRatingRepository, TracksWithLargestNumberOfRatingsRepository tracksWithLargestNumberOfRatingsRepository, UsersWithLargestNumberOfRatingsRepository usersWithLargestNumberOfRatingsRepository, GenresWithLargestNumberOfTracksRepository genresWithLargestNumberOfTracksRepository, GenresWithLargestNumberOfRatingsRepository genresWithLargestNumberOfRatingsRepository, AuthorsWithLargestNumberOfTracksRepository authorsWithLargestNumberOfTracksRepository, AuthorsWithLargestNumberOfRatingsRepository authorsWithLargestNumberOfRatingsRepository) {
        this.tracksWithLargestAverageRatingRepository = tracksWithLargestAverageRatingRepository;
        this.tracksWithLargestNumberOfRatingsRepository = tracksWithLargestNumberOfRatingsRepository;
        this.usersWithLargestNumberOfRatingsRepository = usersWithLargestNumberOfRatingsRepository;
        this.genresWithLargestNumberOfTracksRepository = genresWithLargestNumberOfTracksRepository;
        this.genresWithLargestNumberOfRatingsRepository = genresWithLargestNumberOfRatingsRepository;
        this.authorsWithLargestNumberOfTracksRepository = authorsWithLargestNumberOfTracksRepository;
        this.authorsWithLargestNumberOfRatingsRepository = authorsWithLargestNumberOfRatingsRepository;
    }

    @GetMapping(value = "tracks-with-largest-number-of-ratings", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<TracksWithLargeNumberOfRating>> getTracksWithLargestNumberOfRating() throws SQLException {
        var tracks = tracksWithLargestNumberOfRatingsRepository.getTracksWithLargestNumberOfRating();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "tracks-with-largest-largest-average-rating", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<TracksWithLargeAverageRating>> getTracksWithLargestAverageRating() throws SQLException {
        var tracks = tracksWithLargestAverageRatingRepository.getTracksWithLargestAverageRating();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }


    @GetMapping(value = "genres-with-largest-number-of-tracks", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<GenresWithLargeNumberOfTrack>> getGenresWithLargestNumberOfTracks() throws SQLException {
        var tracks = genresWithLargestNumberOfTracksRepository.genresWithLargestNumberOfTracks();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "genres-with-largest-number-of-ratings", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<GenresWithLargeNumberOfRating>> getGenresWithLargestNumberOfRatings() throws SQLException {
        var tracks = genresWithLargestNumberOfRatingsRepository.genresWithLargestNumberOfRatings();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "authors-with-largest-number-of-tracks", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<AuthorsWithLargeNumberOfTrack>> getAuthorsWithLargestNumberOfTracks() throws SQLException {
        var tracks = authorsWithLargestNumberOfTracksRepository.authorsWithLargestNumberOfTracks();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "authors-with-largest-number-of-ratings", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<AuthorsWithLargeNumberOfRating>> getAuthorsWithLargestNumberOfRatings() throws SQLException {
        var tracks = authorsWithLargestNumberOfRatingsRepository.authorsWithLargestNumberOfRatings();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "users-with-largest-number-of-ratings", produces = "application/json")
    @Transactional(readOnly = true)
    public ResponseEntity<Collection<UsersWithLargeNumberOfRating>> getUsersWithLargestNumberOfRatings() throws SQLException {
        var tracks = usersWithLargestNumberOfRatingsRepository.usersWithLargestNumberOfRatings();
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

}
