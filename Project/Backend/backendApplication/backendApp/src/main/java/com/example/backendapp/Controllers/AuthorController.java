package com.example.backendapp.Controllers;

import com.example.backendapp.Entities.Author;
import com.example.backendapp.Services.Interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Collection<Author> getAuthors() {
        return authorService.getAuthors();
    }
}
