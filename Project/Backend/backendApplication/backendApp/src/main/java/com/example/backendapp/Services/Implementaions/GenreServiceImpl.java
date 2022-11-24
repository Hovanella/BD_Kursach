package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Repositories.GenreRepository;
import com.example.backendapp.Services.Interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Collection<Genre> getGenres() {
        return genreRepository.findAll();
    }
}
