package com.example.backendapp.Services.Implementaions;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.DTO.UnauthorizedUser;
import com.example.backendapp.DTO.UnregisteredUser;
import com.example.backendapp.Entities.User;
import com.example.backendapp.Repositories.PlaylistRepository;
import com.example.backendapp.Repositories.RatingRepository;
import com.example.backendapp.Repositories.UserRepository;
import com.example.backendapp.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final RatingRepository ratingRepository, final PlaylistRepository playlistRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User findUserById(final Long id) {
        final User userById = this.userRepository.findUserById(id);
        return userById;
    }

    @Override
    public void login(final UnauthorizedUser unauthorizedUser) {

        final String login = unauthorizedUser.getLogin();
        final String password = unauthorizedUser.getPassword();

        if (null == userRepository.GetUserIdByLogin(login)) {
            throw new BadCredentialsException("User with login " + login + " not found");
        }

        final User userFromRepository = this.userRepository.findUserById(this.userRepository.GetUserIdByLoginAndPassword(login,password));
        if (null == userFromRepository) {
            throw new BadCredentialsException("Wrong password");
        }
        System.out.println(unauthorizedUser.getPassword() + " " + userFromRepository.getPassword());
    }

    @Override
    public void register(final UnregisteredUser user) {

        if (null != userRepository.GetUserIdByLogin(user.getLogin())) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " already exists");
        }

        try {
            this.userRepository.saveUser(user.getLogin(),user.getPassword(),user.getEmail(),user.getRoleId());
        } catch (final DataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Integer getUserTrackRating(final Long userId, final Long ratingId) {
         final var rate = this.ratingRepository.getUserTrackRating(userId, ratingId);
         if (null == rate) {
             return -1;
         }
            return rate;
    }

    @Override
    public Collection<PlaylistDTO> getUserPlaylists(final Long userId) {

        final var PlaylistDTOs = this.playlistRepository.getUserPlaylists(userId).stream().map(playlist -> {
            final PlaylistDTO playlistDTO = this.modelMapper.map(playlist, PlaylistDTO.class);
            playlistDTO.setAuthorName(playlist.getUser().getLogin());
            return playlistDTO;
        }).collect(Collectors.toList());

        return PlaylistDTOs;
    }
}
