package com.example.backendapp.Repositories;

import com.example.backendapp.Config.ClientDataSource;
import com.example.backendapp.Entities.TrackWithUserRating;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class TrackWithUserRatingRepository {


    public TrackWithUserRatingRepository() throws SQLException {
    }


    public Collection<TrackWithUserRating> findTracksForUser(@Param("user_id_") Long userId, @Param("tracks_to_skip_") Long tracksToSkipNumber, @Param("tracks_to_fetch_") Long tracksToFetchNumber, @Param("search_by_") String searchBy, @Param("search_value_") String searchValue, @Param("order_") String order, @Param("min_rate_") Long minRate, @Param("max_rate_") Long maxRate) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.Get_Tracks_For_User(?,?,?,?,?,?,?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setLong(2, tracksToSkipNumber);
        stmt.setLong(3, tracksToFetchNumber);
        stmt.setString(4, searchBy);
        stmt.setString(5, searchValue);
        stmt.setString(6, order);
        stmt.setLong(7, minRate);
        stmt.setLong(8, maxRate);
        stmt.registerOutParameter(9, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(9);

        var tracks = new ArrayList<TrackWithUserRating>();

        while (rs.next()) {
            var track = new TrackWithUserRating();
            track.setId(rs.getLong("id"));
            track.setName(rs.getString("name"));
            track.setAuthor_name(rs.getString("author_name"));
            track.setGenre_name(rs.getString("genre_name"));
            track.setRate(rs.getLong("rate"));
            tracks.add(track);
        }
        clientConnection.close();
        stmt.close();
        rs.close();
        return tracks;
    }

    public TrackWithUserRating setTrackRating(@Param("track_id_") Long TrackId, @Param("user_id_") Long userId, @Param("rate_") Long rating) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.SET_TRACK_RATING(?,?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setLong(2, TrackId);
        stmt.setLong(3, rating);
        stmt.registerOutParameter(4, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(4);

        var track = new TrackWithUserRating();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setAuthor_name(rs.getString("author_name"));
        track.setGenre_name(rs.getString("genre_name"));
        track.setRate(rs.getLong("rate"));

        clientConnection.close();
        stmt.close();
        rs.close();
        return track;

    }


    public TrackWithUserRating updateTrackRating(@Param("rating_id_") Long RatingId, @Param("rating") Long rating) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.UPDATE_TRACK_RATING(?,?,?)}");
        stmt.setLong(1, RatingId);
        stmt.setLong(2, rating);
        stmt.registerOutParameter(3, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(3);

        var track = new TrackWithUserRating();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setAuthor_name(rs.getString("author_name"));
        track.setGenre_name(rs.getString("genre_name"));
        track.setRate(rs.getLong("rate"));

        clientConnection.close();
        stmt.close();
        rs.close();

        return track;
    }


    public Collection<TrackWithUserRating> getPlaylistTracks(@Param("playlist_id_") Long playlistId, @Param("user_id_") Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.GET_PLAYLIST_TRACKS_FOR_USER(?,?,?)}");
        stmt.setLong(1, playlistId);
        stmt.setLong(2, userId);
        stmt.registerOutParameter(3, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(3);

        var tracks = new ArrayList<TrackWithUserRating>();

        while (rs.next()) {
            var track = new TrackWithUserRating();
            track.setId(rs.getLong("id"));
            track.setName(rs.getString("name"));
            track.setAuthor_name(rs.getString("author_name"));
            track.setGenre_name(rs.getString("genre_name"));
            track.setRate(rs.getLong("rate"));
            tracks.add(track);
        }
        clientConnection.close();
        stmt.close();
        rs.close();


        return tracks;
    }


    public TrackWithUserRating deleteTrackFromPlaylist(@Param("playlist_id_") Long id, @Param("track_id_") Long trackId, @Param("user_id_") Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.DELETE_TRACK_FROM_PLAYLIST(?,?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setLong(2, id);
        stmt.setLong(3, trackId);
        stmt.registerOutParameter(4, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(4);

        var track = new TrackWithUserRating();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setAuthor_name(rs.getString("author_name"));
        track.setGenre_name(rs.getString("genre_name"));
        track.setRate(rs.getLong("rate"));
        clientConnection.close();
        stmt.close();
        rs.close();


        return track;

    }


    public TrackWithUserRating addTrackToPlaylist(@Param("playlist_id_") Long id, @Param("track_id_") Long trackId, @Param("user_id_") Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.ADD_TRACK_TO_PLAYLIST(?,?,?,?)}");
        stmt.setLong(1, userId);
        stmt.setLong(2, id);
        stmt.setLong(3, trackId);
        stmt.registerOutParameter(4, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(4);

        var track = new TrackWithUserRating();
        rs.next();
        track.setId(rs.getLong("id"));
        track.setName(rs.getString("name"));
        track.setAuthor_name(rs.getString("author_name"));
        track.setGenre_name(rs.getString("genre_name"));
        track.setRate(rs.getLong("rate"));
        clientConnection.close();
        stmt.close();
        rs.close();

        return track;
    }
}