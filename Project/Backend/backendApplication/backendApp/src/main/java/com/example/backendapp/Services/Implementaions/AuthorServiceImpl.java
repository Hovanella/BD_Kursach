package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.Entities.Author;
import com.example.backendapp.Repositories.AuthorRepository;
import com.example.backendapp.Services.Interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Collection<Author> getAuthors() throws SQLException {
        return authorRepository.findAllAuthors();
    }

    @Override
    @Transactional(readOnly = true)
    public Author addAuthor(String name) throws SQLException {
        return authorRepository.saveAuthor(name);
    }


}
