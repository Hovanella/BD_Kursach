package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.UsersWithLargeNumberOfRating;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class UsersWithLargestNumberOfRatingsRepository {

    public Collection<UsersWithLargeNumberOfRating> usersWithLargestNumberOfRatings() throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_USERS_WITH_LARGEST_NUMBER_OF_RATING(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<UsersWithLargeNumberOfRating> users = new java.util.ArrayList<>();
        while (rs.next()) {
            UsersWithLargeNumberOfRating user = new UsersWithLargeNumberOfRating();
            user.setId(rs.getLong("ID"));
            user.setCountRate(rs.getLong("COUNT(RATE)"));
            user.setLogin(rs.getString("LOGIN"));
            users.add(user);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return users;
    }
}
