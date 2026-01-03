package com.BryceJensenius.MediaOrganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BryceJensenius.MediaOrganizer.service.SnsService;

@RestController
@RequestMapping("/publishSns")
@CrossOrigin
public class SnsController {
    @Autowired
    private SnsService snsService;

    @PostMapping
    public void publishToSns(){
        snsService.publishSnsMessage();
    }
}