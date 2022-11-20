package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Blob;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Procedure(procedureName = "GET_TRACK_COUNT", outputParameterName = "track_count_")
    long getTrackCount(@Param("user_id_") Long userId, @Param("search_by_") String searchBy, @Param("search_value_") String searchValue, @Param("min_rate_") Long minRate, @Param("max_rate_") Long maxRate);

    @Procedure(procedureName = "GET_TRACK_FILE", outputParameterName = "track_file_")
    Blob getTrackFile(@Param("track_id_") Long trackId);


}
