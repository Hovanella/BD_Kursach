package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query(value = "SELECT * FROM TRACKS order by ID OFFSET :tracksToSkipNumber ROWS FETCH NEXT :tracksToFetchNumber rows only", nativeQuery = true)
    List<Track> findPageable(Long tracksToSkipNumber, Long tracksToFetchNumber);

}
