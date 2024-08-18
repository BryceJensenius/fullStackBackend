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

    @GetMapping("/movie/{title}")
    public String[] getMovie(@PathVariable String title) {
        String[] guessList = omdbService.getMovieDetails(title);
        return Arrays.copyOfRange(guessList, 0, Math.min(4, guessList.length));//shorten the list to 4 elements
    }
}
