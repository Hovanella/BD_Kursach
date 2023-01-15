package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.GenresWithLargeNumberOfRating;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class GenresWithLargestNumberOfRatingsRepository {


    public Collection<GenresWithLargeNumberOfRating> genresWithLargestNumberOfRatings() throws SQLException {

        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_GENRES_WITH_LARGEST_NUMBER_OF_RATING(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<GenresWithLargeNumberOfRating> genres = new java.util.ArrayList<>();
        while (rs.next()) {
            GenresWithLargeNumberOfRating genre = new GenresWithLargeNumberOfRating();
            genre.setName(rs.getString("NAME"));
            genre.setRatingCount(rs.getLong("RATING_COUNT"));
            genre.setId(rs.getLong("ID"));
            genres.add(genre);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return genres;
    }

}

