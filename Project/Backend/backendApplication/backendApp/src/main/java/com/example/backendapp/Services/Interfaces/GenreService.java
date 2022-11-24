package com.example.backendapp.Services.Interfaces;


import com.example.backendapp.Entities.Genre;

import java.util.Collection;

public interface GenreService {

    Collection<Genre> getGenres();
}
