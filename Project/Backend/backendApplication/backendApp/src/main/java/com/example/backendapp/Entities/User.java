package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Setter
@Getter
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    @Column(name = "LOGIN", nullable = false, length = 30)
    private String login;

    @Column(name = "PASSWORD", nullable = false, length = 32)
    private String password;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Collection<Playlist> playlists = new ArrayList<>();

}