package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.UsersWithLargeNumberOfRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UsersWithLargestNumberOfRatingsRepository extends JpaRepository<UsersWithLargeNumberOfRating, Long> {
    @Procedure(procedureName = "GET_USERS_WITH_LARGEST_NUMBER_OF_RATING", refCursor = true, outputParameterName = "users_")
    Collection<UsersWithLargeNumberOfRating> usersWithLargestNumberOfRatings();
}
