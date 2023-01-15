package com.example.backendapp.Repositories;

import com.example.backendapp.Config.ClientDataSource;
import com.example.backendapp.Config.UnauthorizedUserDataSource;
import com.example.backendapp.Entities.Role;
import com.example.backendapp.Entities.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.NUMERIC;
import static java.sql.Types.REF_CURSOR;

@Repository
public class UserRepository {


    public UserRepository() throws SQLException {
    }

    public User findUserById(@Param("user_id_") Long id) throws SQLException {
        Connection unauthorizedUserConnection = UnauthorizedUserDataSource.getConnection();
        java.sql.CallableStatement stmt = unauthorizedUserConnection.prepareCall("{call KURSACH_ADMIN.GET_USER_BY_ID(?,?)}");
        stmt.setLong(1, id);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        User user = new User();


        while (rs.next()) {
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));

            var roleId = rs.getLong("role_id");

            var role = new Role();
            if (roleId == 3) {
                role.setId(3L);
                role.setName("admin");
            } else {
                role.setId(2L);
                role.setName("user");
            }

            user.setRole(role);
        }

        unauthorizedUserConnection.close();
        stmt.close();
        rs.close();
        return user;

    }


    public Long GetUserIdByLogin(@Param("login_") String login) throws SQLException {
        Connection unauthorizedUserConnection = UnauthorizedUserDataSource.getConnection();
        java.sql.CallableStatement stmt = unauthorizedUserConnection.prepareCall("{call KURSACH_ADMIN. GET_USER_ID_BY_LOGIN(?,?)}");
        stmt.setString(1, login);
        stmt.registerOutParameter(2, NUMERIC);
        stmt.execute();

        var rs = stmt.getLong(2) == 0 ? null : stmt.getLong(2);

        unauthorizedUserConnection.close();
        stmt.close();
        return rs;
    }


    public void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id) throws SQLException {
        Connection unauthorizedUserConnection = UnauthorizedUserDataSource.getConnection();
        java.sql.CallableStatement stmt = unauthorizedUserConnection.prepareCall("{call KURSACH_ADMIN.REGISTER_USER(?,?,?,?)}");
        stmt.setString(1, login);
        stmt.setString(2, password);
        stmt.setString(3, email);
        stmt.setLong(4, id);
        stmt.execute();
        unauthorizedUserConnection.close();
        stmt.close();
    }

    public User findByLogin(String username) throws SQLException {
        Connection clientConnection = ClientDataSource.getConnection();
        java.sql.CallableStatement stmt = clientConnection.prepareCall("{call KURSACH_ADMIN.GET_USER_BY_LOGIN(?,?)}");
        stmt.setString(1, username);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);
        User user = new User();


        while (rs.next()) {
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));

            var roleId = rs.getLong("role_id");

            var role = new Role();
            if (roleId == 3) {
                role.setId(3L);
                role.setName("admin");
            } else {
                role.setId(2L);
                role.setName("user");
            }

            user.setRole(role);
        }

        clientConnection.close();
        stmt.close();
        rs.close();

        return user;
    }

    public Collection<String> getLogins() throws SQLException {
        Connection connection = UnauthorizedUserDataSource.getConnection();
        java.sql.CallableStatement stmt = connection.prepareCall("{call KURSACH_ADMIN.GET_LOGINS(?)}");

        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);
        Collection<String> logins = new java.util.ArrayList<>();

        while (rs.next()) {
            logins.add(rs.getString("login"));
        }

        connection.close();
        stmt.close();
        rs.close();
        return logins;

    }
}
