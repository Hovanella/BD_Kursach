package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.AuthorsWithLargeNumberOfTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AuthorsWithLargestNumberOfTracksRepository extends JpaRepository<AuthorsWithLargeNumberOfTrack, Long> {
    @Procedure(procedureName = "GET_AUTHORS_WITH_LARGEST_NUMBER_OF_TRACKS", refCursor = true, outputParameterName = "authors_")
    Collection<AuthorsWithLargeNumberOfTrack> authorsWithLargestNumberOfTracks();
}

