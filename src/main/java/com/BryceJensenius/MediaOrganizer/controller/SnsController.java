package com.BryceJensenius.MediaOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.model.SnsBody;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.service.SnsService;
import com.BryceJensenius.MediaOrganizer.service.UserService;

@RestController
@RequestMapping("/publishSns")
@CrossOrigin
public class SnsController {
    @Autowired
    private SnsService snsService;

    @Autowired
    private UserService userService;

    @PostMapping
    public void publishToSns(@RequestHeader("Authorization") String authHeader, @RequestBody SnsBody snsBody){
        // User user = userService.getUserFromAuthorizationHeader(authHeader);
        // if(user == null){
        //     return;
        // }
        // snsService.publishSnsMessage(snsBody);
    }
}