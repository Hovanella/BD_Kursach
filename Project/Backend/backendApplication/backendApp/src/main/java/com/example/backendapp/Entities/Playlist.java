package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Setter
@Getter
@Table(name = "PLAYLISTS")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @ManyToMany(mappedBy = "playlists")
    private Collection<Track> tracks;


}