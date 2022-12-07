package com.example.backendapp.Repositories;

import com.example.backendapp.Config.ClientDataSource;
import com.example.backendapp.Entities.Rating;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.REF_CURSOR;

@Repository
@Transactional
public class RatingRepository {


    public RatingRepository() throws SQLException {
    }


    public Rating getRatingForTrackFromUser(@Param("user_id_") Long userId, @Param("track_id_") Long TrackId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN. GET_RATING_FOR_TRACK_FROM_USER(?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setLong(2, TrackId);
        stmt.registerOutParameter(3, REF_CURSOR);
        stmt.execute();

        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(3);

        Rating rating = new Rating();
        if (!rs.next()) return null;
        rating.setId(rs.getLong("id"));

        clientConnection.close();
        stmt.close();
        rs.close();
        return rating;
    }
}

