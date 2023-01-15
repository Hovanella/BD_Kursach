package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mapping for DB view
 */
@Entity
@Setter
@Getter
@Immutable
@Table(name = "USERS_WITH_LARGE_NUMBER_OF_RATINGS")
public class UsersWithLargeNumberOfRating {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LOGIN", nullable = false, length = 30)
    private String login;

    @Column(name = "\"COUNT(RATE)\"")
    private Long countRate;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Long getCountRate() {
        return countRate;
    }

    public UsersWithLargeNumberOfRating() {
    }
}