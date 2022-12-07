package com.example.backendapp.Controllers;

import com.example.backendapp.Entities.Author;
import com.example.backendapp.Services.Interfaces.AuthorService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/authors/")
@CrossOrigin("*")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping(value = "/", produces = "application/json")
    public Collection<Author> getAuthors() throws SQLException {
        return authorService.getAuthors();
    }

    @PostMapping(value = "/", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Author addAuthor(@RequestBody JsonNode jsonNode) throws SQLException {
        var name = jsonNode.get("name").asText();
        return authorService.addAuthor(name);
    }
}
