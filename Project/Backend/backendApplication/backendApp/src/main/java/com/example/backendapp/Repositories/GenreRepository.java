package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
