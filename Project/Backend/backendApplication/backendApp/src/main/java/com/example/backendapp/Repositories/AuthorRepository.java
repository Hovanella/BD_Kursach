package com.example.backendapp.Repositories;

import com.example.backendapp.Config.AdminDataSource;
import com.example.backendapp.Entities.Author;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Types.REF_CURSOR;

@Repository
public class AuthorRepository {


    public AuthorRepository() throws SQLException {
    }


    public Author saveAuthor(@Param("name_") String name) throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.ADD_AUTHOR(?,?)}");
        stmt.setString(1, name);
        stmt.registerOutParameter(2, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(2);

        var author = new Author();
        rs.next();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));

        adminConnection.close();
        stmt.close();
        rs.close();


        return author;

    }

    public Collection<Author> findAllAuthors() throws SQLException {
        Connection adminConnection = AdminDataSource.getConnection();
        java.sql.CallableStatement stmt = adminConnection.prepareCall("{call KURSACH_ADMIN.GET_AUTHORS(?)}");
        stmt.registerOutParameter(1, REF_CURSOR);
        stmt.execute();
        java.sql.ResultSet rs = (java.sql.ResultSet) stmt.getObject(1);

        var authors = new java.util.ArrayList<Author>();

        while (rs.next()) {
            var author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            authors.add(author);
        }
        adminConnection.close();
        stmt.close();
        rs.close();
        return authors;
    }
}
