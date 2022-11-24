package com.example.backendapp.Controllers;


import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Services.Interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/genres/")
@CrossOrigin("*")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Collection<Genre> getGenres() {
        return genreService.getGenres();
    }
}
