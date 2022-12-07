package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Entity
@Setter
@Getter
@Immutable
@Table(name = "TRACKS_WITH_LARGE_AVERAGE_RATING")
public class TracksWithLargeAverageRating {
    @Id
    @Column(name = "TRACK_ID", nullable = false)
    private Long trackId;

    @Column(name = "TRACK_NAME", nullable = false, length = 30)
    private String trackName;

    @Column(name = "GENRE_NAME", nullable = false, length = 30)
    private String genreName;

    @Column(name = "AUTHOR_NAME", nullable = false, length = 30)
    private String authorName;

    @Column(name = "AVERAGE_RATE", nullable = false)
    private BigDecimal averageRate;


    protected TracksWithLargeAverageRating() {
    }
}