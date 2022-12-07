package com.example.backendapp.Services.Interfaces;


import com.example.backendapp.Entities.Genre;

import java.sql.SQLException;
import java.util.Collection;

public interface GenreService {

    Collection<Genre> getGenres() throws SQLException;

    Genre addGenre(String name) throws SQLException;
}
