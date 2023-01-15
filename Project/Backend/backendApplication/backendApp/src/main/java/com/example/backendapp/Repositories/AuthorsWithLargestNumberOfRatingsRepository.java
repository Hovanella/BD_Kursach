package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.AuthorsWithLargeNumberOfRating;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class AuthorsWithLargestNumberOfRatingsRepository {

    public Collection<AuthorsWithLargeNumberOfRating> authorsWithLargestNumberOfRatings() throws SQLException {

        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_AUTHORS_WITH_LARGEST_NUMBER_OF_RATING(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<AuthorsWithLargeNumberOfRating> authors = new java.util.ArrayList<>();

        while (rs.next()) {
            var author = new AuthorsWithLargeNumberOfRating();
            author.setName(rs.getString("NAME"));
            author.setRatingCount(rs.getLong("RATING_COUNT"));
            author.setId(rs.getLong("ID"));
            authors.add(author);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return authors;
    }
}

