package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Getter
@Setter
@Table(name = "TRACKS")

public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;


    @Column(name = "GENRE_ID", nullable = false)
    private Long genreId;

    @Column(name = "AUTHOR_ID", nullable = false)
    private Long authorId;

    @ManyToMany
    @JoinTable(name = "PLAYLIST_TRACKS",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id"))
    private Collection<Playlist> playlists = new ArrayList<>();


    @Column(name = "TRACK_FILE_ID", nullable = false)
    private Long trackFileId;


}