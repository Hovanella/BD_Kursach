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
@Table(name = "TRACKS_WITH_LARGE_NUMBER_OF_RATINGS")
public class TracksWithLargeNumberOfRating {
    @Id
    @Column(name = "TRACK_ID", nullable = false)
    private Long trackId;

    @Column(name = "TRACK_NAME", nullable = false, length = 30)
    private String trackName;

    @Column(name = "GENRE_NAME", nullable = false, length = 30)
    private String genreName;

    @Column(name = "AUTHOR_NAME", nullable = false, length = 30)
    private String authorName;

    @Column(name = "RATE_COUNT")
    private Long rateCount;

    public Long getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Long getRateCount() {
        return rateCount;
    }

    protected TracksWithLargeNumberOfRating() {
    }
}