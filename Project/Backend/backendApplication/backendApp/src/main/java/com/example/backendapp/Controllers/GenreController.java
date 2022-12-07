package com.example.backendapp.Controllers;


import com.example.backendapp.Entities.Genre;
import com.example.backendapp.Services.Interfaces.GenreService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    public Collection<Genre> getGenres() throws SQLException {
        return genreService.getGenres();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/", produces = "application/json")
    public Genre addGenre(@RequestBody JsonNode jsonNode) throws SQLException {
        var name = jsonNode.get("name").asText();
        return genreService.addGenre(name);
    }

}
