package com.example.backendapp.Controllers;

import com.example.backendapp.DTO.UnauthorizedUser;
import com.example.backendapp.DTO.UnregisteredUser;
import com.example.backendapp.Security.JWTUtil;
import com.example.backendapp.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;

@RestController
@RequestMapping("/users/")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UnauthorizedUser unauthorizedUser) {

        userService.login(unauthorizedUser);
        String token = jwtUtil.generateToken(unauthorizedUser.getLogin());

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody UnregisteredUser unauthorizedUser) throws Exception {
        try {
            userService.register(unauthorizedUser);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        String token = jwtUtil.generateToken(unauthorizedUser.getLogin());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @GetMapping(value = "is-admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isAdmin() {
        return new ResponseEntity<>(userService.isAdmin(), HttpStatus.OK);
    }

    @GetMapping(value = "logins", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<String>> getLogins() throws SQLException {
        return new ResponseEntity<>(userService.getLogins(), HttpStatus.OK);
    }


}
