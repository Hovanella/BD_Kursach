package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Procedure(name = "GET_RATING_FOR_TRACK_FROM_USER")
    Rating getRatingForTrackFromUser(@Param("user_id_") Long userId, @Param("track_id_") Long TrackId);
}

