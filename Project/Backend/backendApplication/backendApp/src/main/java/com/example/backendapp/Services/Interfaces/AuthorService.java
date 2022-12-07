package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.Entities.Author;

import java.sql.SQLException;
import java.util.Collection;

public interface AuthorService {

    Collection<Author> getAuthors() throws SQLException;

    Author addAuthor(String name) throws SQLException;
}
