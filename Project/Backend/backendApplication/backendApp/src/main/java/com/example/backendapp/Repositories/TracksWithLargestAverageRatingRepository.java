package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.TracksWithLargeAverageRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TracksWithLargestAverageRatingRepository extends JpaRepository<TracksWithLargeAverageRating, Long> {


    @Procedure(procedureName = "GET_TRACKS_WITH_LARGEST_AVERAGE_RATING", refCursor = true, outputParameterName = "tracks_")
    Collection<TracksWithLargeAverageRating> getTracksWithLargestAverageRating();
}
