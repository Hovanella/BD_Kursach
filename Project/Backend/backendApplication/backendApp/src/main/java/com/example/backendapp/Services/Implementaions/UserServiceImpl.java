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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final PlaylistRepository playlistRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RatingRepository ratingRepository, PlaylistRepository playlistRepository, ModelMapper modelMapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.playlistRepository = playlistRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        User userById = userRepository.findUserById(id);
        return userById;
    }

    @Override
    @Transactional(readOnly = true)
    public void login(UnauthorizedUser unauthorizedUser) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(unauthorizedUser.getLogin(), unauthorizedUser.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public void register(UnregisteredUser user) {

        if (null != this.userRepository.GetUserIdByLogin(user.getLogin())) {
            throw new IllegalArgumentException("User with login " + user.getLogin() + " already exists");
        }

        try {
            userRepository.saveUser(user.getLogin(), passwordEncoder.encode(user.getPassword()), user.getEmail(), user.getRoleId());
        } catch (DataAccessException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<PlaylistDTO> getUserPlaylists(Long userId) {

        var PlaylistDTOs = playlistRepository.getUserPlaylists(userId).stream().map(playlist -> {
            PlaylistDTO playlistDTO = modelMapper.map(playlist, PlaylistDTO.class);
            playlistDTO.setAuthorName(playlist.getUser().getLogin());
            return playlistDTO;
        }).collect(Collectors.toList());

        return PlaylistDTOs;
    }

    @Override
    public Boolean isAdmin() {
        final var currentUserDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (null == currentUserDetails)
            throw new BadCredentialsException("Not authorized");
        final var userRole = currentUserDetails.getAuthorities().toArray()[0].toString();

        return userRole.equals("ROLE_ADMIN");

    }

}
