package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.Genre;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class GenreRepository {


    public GenreRepository() throws SQLException {
    }


    public Genre saveGenre(String name) throws SQLException {

        Connection adminConnection = AdminDataSource.getConnection();

        java.sql.CallableStatement prepareCall = adminConnection.prepareCall("{call KURSACH_ADMIN.ADD_GENRE(?,?)}");

        prepareCall.setString(1, name);
        prepareCall.registerOutParameter(2, REF_CURSOR);
        prepareCall.execute();

        java.sql.ResultSet rs = (java.sql.ResultSet) prepareCall.getObject(2);

        var genre = new Genre();
        rs.next();

        genre.setId(rs.getLong("id"));
        genre.setName(rs.getString("name"));

        adminConnection.close();
        prepareCall.close();
        rs.close();

        return genre;
    }

    public Collection<Genre> findAllGenres() throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_GENRES(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);

        var genres = new java.util.ArrayList<Genre>();

        while (rs.next()) {
            var genre = new Genre();
            genre.setId(rs.getLong("id"));
            genre.setName(rs.getString("name"));
            genres.add(genre);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return genres;
    }
}
