package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.PlaylistDTO;
import com.example.backendapp.DTO.UnauthorizedUser;
import com.example.backendapp.DTO.UnregisteredUser;
import com.example.backendapp.Security.JWTUtil;
import com.example.backendapp.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users/")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserController(final UserService userService, final AuthenticationManager authenticationManager, final JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody final UnauthorizedUser unauthorizedUser) {

        try{
            this.userService.login(unauthorizedUser);
        } catch (final BadCredentialsException e){
            return new ResponseEntity<>("User with login " + unauthorizedUser.getLogin() + " doesn't exist", HttpStatus.BAD_REQUEST);
        }

        final String token = this.jwtUtil.generateToken(unauthorizedUser.getLogin());
        return new ResponseEntity<>(token, HttpStatus.OK);

    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody final UnregisteredUser unauthorizedUser) throws Exception {
        try{
            this.userService.register(unauthorizedUser);
        }catch (final Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }


    @GetMapping(value = "{userId}/tracks/{TrackId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getUserRating(@PathVariable("userId") final Long userId, @PathVariable("TrackId") final Long ratingId) {

        final var rating = this.userService.getUserTrackRating(userId, ratingId);
        return new ResponseEntity<>(rating, HttpStatus.OK);

    }

    @GetMapping(value = "{userId}/playlists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PlaylistDTO>> getUserPlaylists(@PathVariable("userId") final Long userId) {
        final var playlists = this.userService.getUserPlaylists(userId) ;
        return new ResponseEntity<>(playlists, HttpStatus.OK);
    }

}