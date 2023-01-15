package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.TracksWithLargeAverageRating;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class TracksWithLargestAverageRatingRepository {


    /* @Procedure(procedureName = "GET_TRACKS_WITH_LARGEST_AVERAGE_RATING", refCursor = true, outputParameterName = "tracks_")*/
    public Collection<TracksWithLargeAverageRating> getTracksWithLargestAverageRating() throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_TRACKS_WITH_LARGEST_AVERAGE_RATING(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<TracksWithLargeAverageRating> tracks = new java.util.ArrayList<>();
        while (rs.next()) {
            TracksWithLargeAverageRating track = new TracksWithLargeAverageRating();
            track.setTrackId(rs.getLong("track_id"));
            track.setTrackName(rs.getString("track_name"));
            track.setGenreName(rs.getString("genre_name"));
            track.setAuthorName(rs.getString("author_name"));
            track.setAverageRate(rs.getBigDecimal("AVERAGE_RATE"));
            tracks.add(track);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return tracks;
    }

}
