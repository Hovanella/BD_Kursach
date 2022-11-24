package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.Entities.Author;

import java.util.Collection;

public interface AuthorService {

    Collection<Author> getAuthors();
}
