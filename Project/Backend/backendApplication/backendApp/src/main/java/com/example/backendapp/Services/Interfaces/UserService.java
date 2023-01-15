package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.UnauthorizedUser;
import com.example.backendapp.DTO.UnregisteredUser;
import com.example.backendapp.Entities.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UserService {

    User findUserById(Long id) throws SQLException;

    void login(UnauthorizedUser unauthorizedUser);

    void register(UnregisteredUser User) throws SQLException;


    Boolean isAdmin();

    Collection<String> getLogins() throws SQLException;
}
