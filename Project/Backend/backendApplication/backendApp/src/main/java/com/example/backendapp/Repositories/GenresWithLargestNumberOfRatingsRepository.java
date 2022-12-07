package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.GenresWithLargeNumberOfRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GenresWithLargestNumberOfRatingsRepository extends JpaRepository<GenresWithLargeNumberOfRating, Long> {

    @Procedure(procedureName = "GET_GENRES_WITH_LARGEST_NUMBER_OF_RATING", refCursor = true, outputParameterName = "genres_")
    Collection<GenresWithLargeNumberOfRating> genresWithLargestNumberOfRatings();
}

