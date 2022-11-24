package com.example.backendapp.Services.Interfaces;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.DTO.UnauthorizedUser;
import com.example.backendapp.DTO.UnregisteredUser;
import com.example.backendapp.Entities.User;

import java.util.Collection;

public interface UserService {

    User findUserById(Long id);

    void login(UnauthorizedUser unauthorizedUser);

    void register(UnregisteredUser User);


    Collection<PlaylistDTO> getUserPlaylists(Long userId);

    Boolean isAdmin();
}
