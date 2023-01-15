package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Config.ClientDataSource;
import com.example.backendapp.Entities.Track;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Types.*;

@Repository
public class TrackRepository {


    public TrackRepository() throws SQLException {
    }


    public long getTrackCount(@Param("user_id_") Long userId, @Param("search_by_") String searchBy, @Param("search_value_") String searchValue, @Param("min_rate_") Long minRate, @Param("max_rate_") Long maxRate) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.GET_TRACK_COUNT(?,?,?,?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setString(2, searchBy);
        stmt.setString(3, searchValue);
        stmt.setLong(4, minRate);
        stmt.setLong(5, maxRate);
        stmt.registerOutParameter(6, NUMERIC);
        stmt.execute();

        var count = stmt.getLong(6);

        clientConnection.close();
        stmt.close();

        return count;

    }


    public Blob getTrackFile(@Param("track_id_") Long trackId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN. GET_TRACK_FILE(?,?)}");
        stmt.setLong(1, trackId);
        stmt.registerOutParameter(2, BLOB);
        stmt.execute();

        var rs = stmt.getBlob(2);
        clientConnection.close();
        stmt.close();
        return rs;
    }


    public Long uploadTrackFile(@Param("track_file_") byte[] fileBytes) throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN. CREATE_TRACK_FILE(?,?)}");
        stmt.setBytes(1, fileBytes);
        stmt.registerOutParameter(2, NUMERIC);
        stmt.execute();

        var rs = stmt.getLong(2);
        adminConnection.close();
        stmt.close();
        return rs;
    }


    public Track createTrack(@Param("track_name_") String name, @Param("genre_id_") long genreId, @Param("author_id_") long authorId, @Param("track_file_id_") long trackFileId) throws SQLException {
        Connection clientConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN. CREATE_TRACK(?,?,?,?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, genreId);
        stmt.setLong(3, authorId);
        stmt.setLong(4, trackFileId);
        stmt.registerOutParameter(5, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(5);

        var track = new Track();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setGenreId(rs.getLong("genre_id"));
        track.setAuthorId(rs.getLong("author_id"));
        track.setTrackFileId(rs.getLong("track_file_id"));

        clientConnection.close();
        stmt.close();
        rs.close();

        return track;
    }


    public Track findTrackById(Long id) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN. GET_TRACK_BY_ID(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);

        var track = new Track();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));


        clientConnection.close();
        stmt.close();
        rs.close();
        return track;
    }
}
