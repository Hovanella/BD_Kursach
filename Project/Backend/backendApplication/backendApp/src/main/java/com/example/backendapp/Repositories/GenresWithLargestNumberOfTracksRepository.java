package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.GenresWithLargeNumberOfTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GenresWithLargestNumberOfTracksRepository extends JpaRepository<GenresWithLargeNumberOfTrack, Long> {

    @Procedure(procedureName = "GET_GENRES_WITH_LARGEST_NUMBER_OF_TRACKS", refCursor = true, outputParameterName = "genres_")
    Collection<GenresWithLargeNumberOfTrack> genresWithLargestNumberOfTracks();
}
