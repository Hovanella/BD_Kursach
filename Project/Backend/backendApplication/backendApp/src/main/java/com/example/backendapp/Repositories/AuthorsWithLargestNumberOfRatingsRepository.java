package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.AuthorsWithLargeNumberOfRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuthorsWithLargestNumberOfRatingsRepository extends JpaRepository<AuthorsWithLargeNumberOfRating, Long> {


    @Procedure(procedureName = "GET_AUTHORS_WITH_LARGEST_NUMBER_OF_RATING", refCursor = true, outputParameterName = "authors_")
    Collection<AuthorsWithLargeNumberOfRating> authorsWithLargestNumberOfRatings();
}

