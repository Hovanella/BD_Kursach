package com.example.backendapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.backendapp.Entities.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedUser implements Serializable {
    private String login;
    private String password;
}