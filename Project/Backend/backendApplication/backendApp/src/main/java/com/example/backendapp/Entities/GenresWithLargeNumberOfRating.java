package com.example.backendapp.Entities;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping for DB view
 */
@Entity
@Immutable
@Table(name = "GENRES_WITH_LARGE_NUMBER_OF_RATINGS")
public class GenresWithLargeNumberOfRating {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "RATING_COUNT")
    private Long ratingCount;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    protected GenresWithLargeNumberOfRating() {
    }
}