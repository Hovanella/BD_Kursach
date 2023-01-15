package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.AuthorsWithLargeNumberOfTrack;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class AuthorsWithLargestNumberOfTracksRepository {

    public Collection<AuthorsWithLargeNumberOfTrack> authorsWithLargestNumberOfTracks() throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_AUTHORS_WITH_LARGEST_NUMBER_OF_TRACKS(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<AuthorsWithLargeNumberOfTrack> authors = new java.util.ArrayList<>();

        while (rs.next()) {
            var author = new AuthorsWithLargeNumberOfTrack();
            author.setName(rs.getString("NAME"));
            author.setTracksCount(rs.getLong("TRACKS_COUNT"));
            author.setId(rs.getLong("ID"));
            authors.add(author);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return authors;
    }

}

