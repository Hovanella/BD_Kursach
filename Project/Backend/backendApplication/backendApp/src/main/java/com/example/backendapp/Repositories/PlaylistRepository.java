package com.example.backendapp.Repositories;

import com.example.backendapp.Config.ClientDataSource;
import com.example.backendapp.Entities.Playlist;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class PlaylistRepository {


    public PlaylistRepository() throws SQLException {
    }


    public Collection<Playlist> getAllUserPlaylists(@Param("user_id_") Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.GET_PLAYLISTS(?,?)}");
        stmt.setLong(1, userId);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);

        var playlists = new ArrayList<Playlist>();

        while (rs.next()) {
            var playlist = new Playlist();
            playlist.setId(rs.getLong("id"));
            playlist.setName(rs.getString("name"));
            playlist.setUserId(rs.getLong("user_id"));
            playlists.add(playlist);
        }
        clientConnection.close();
        stmt.close();
        rs.close();
        return playlists;

    }


    public Playlist getPlaylistById(@Param("playlist_id") Long id) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.GET_PLAYLIST_BY_ID(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);

        var playlist = new Playlist();
        rs.next();
        playlist.setId(rs.getLong("id"));
        playlist.setName(rs.getString("name"));
        playlist.setUserId(rs.getLong("user_id"));
        clientConnection.close();
        stmt.close();
        rs.close();
        return playlist;
    }

    public Playlist createPlaylist(String name, Long userId) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.CREATE_PLAYLIST(?,?,?)}");
        stmt.setString(1, name);
        stmt.setLong(2, userId);
        stmt.registerOutParameter(3, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(3);

        var playlist = new Playlist();
        rs.next();
        playlist.setId(rs.getLong("id"));
        playlist.setName(rs.getString("name"));
        playlist.setUserId(rs.getLong("user_id"));

        stmt.close();
        rs.close();
        clientConnection.close();

        return playlist;
    }
}
