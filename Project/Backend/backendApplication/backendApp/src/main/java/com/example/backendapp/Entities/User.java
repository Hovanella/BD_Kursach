package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "GET_USER_BY_ID",
                procedureName = "GET_USER_BY_ID",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id_", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "user_out", type = void.class)
                }
        )
)
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