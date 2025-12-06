package com.BryceJensenius.MediaOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.model.AuthorizationRequest;
import com.BryceJensenius.MediaOrganizer.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /*
        Login a user with their username and password
        Returns a session token which is valid for 24 hours
        Returns null if login fails
     */
    @GetMapping("/login")
    public String getMovie(@RequestBody AuthorizationRequest authRequest) {
        String sessionToken = userService.login(authRequest);
        return sessionToken;
    }

    /*
        Signup a user with their desired username and password
        Returns a session token which is valid for 24 hours
        Returns null if signup fails
     */
    @GetMapping("/signup")
    public String signup(@RequestBody AuthorizationRequest authRequest) {
        String sessionToken = userService.signup(authRequest);
        return sessionToken;
    }
}
