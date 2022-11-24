package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.TrackWithUserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TrackWithUserRatingRepository extends JpaRepository<TrackWithUserRating, Long> {

    @Procedure(name = "GET_TRACKS_FOR_USER")
    Collection<TrackWithUserRating> findTracksForUser(@Param("user_id_") Long userId, @Param("tracks_to_skip_") Long tracksToSkipNumber, @Param("tracks_to_fetch_") Long tracksToFetchNumber, @Param("search_by_") String searchBy, @Param("search_value_") String searchValue, @Param("order_") String order, @Param("min_rate_") Long minRate, @Param("max_rate_") Long maxRate);

    @Procedure(name = "SET_TRACK_RATING")
    TrackWithUserRating setTrackRating(@Param("track_id_") Long TrackId, @Param("user_id_") Long userId, @Param("rate_") Long rating);

    @Procedure(name = "UPDATE_TRACK_RATING")
    TrackWithUserRating updateTrackRating(@Param("rating_id_") Long RatingId, @Param("rating") Long rating);
}
