package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT RATE FROM rating WHERE user_id = ?1 AND track_id = ?2", nativeQuery = true)
    Integer getUserTrackRating(Long userId, Long ratingId);
}

