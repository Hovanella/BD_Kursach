package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;


@Immutable
@Getter
@Setter
@Table(name = "TRACKS_WITH_GENRE_AND_AUTHOR")
public class TracksWithGenreAndAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRACK_ID", nullable = false)
    private Long trackId;

    @Column(name = "TRACK_NAME", nullable = false, length = 30)
    private String trackName;

    @Column(name = "GENRE_NAME", nullable = false, length = 30)
    private String genreName;

    @Column(name = "AUTHOR_NAME", nullable = false, length = 30)
    private String authorName;

    public Long getTrackId() {
        return this.trackId;
    }

    public String getTrackName() {
        return this.trackName;
    }

    public String getGenreName() {
        return this.genreName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    protected TracksWithGenreAndAuthor() {
    }
}