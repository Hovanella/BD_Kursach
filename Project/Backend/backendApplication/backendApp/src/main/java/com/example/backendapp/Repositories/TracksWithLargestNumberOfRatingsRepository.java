package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.TracksWithLargeNumberOfRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TracksWithLargestNumberOfRatingsRepository extends JpaRepository<TracksWithLargeNumberOfRating, Long> {
    @Procedure(procedureName = "GET_TRACKS_WITH_LARGEST_NUMBER_OF_RATING", refCursor = true, outputParameterName = "tracks_")
    Collection<TracksWithLargeNumberOfRating> getTracksWithLargestNumberOfRating();
}
