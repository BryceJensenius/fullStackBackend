package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonObject;

import java.util.Arrays;

@RestController
@RequestMapping("/api/omdb")
@CrossOrigin
public class OmdbController {

    @Autowired
    private OmdbService omdbService;
    private int numCalls = 0;

    @GetMapping("/movie/{title}")
    public String[] getMovie(@PathVariable String title) {
        String[] guess = omdbService.getMovieDetails(title);
        System.out.println(Arrays.toString(guess));
        return guess;
    }
}
