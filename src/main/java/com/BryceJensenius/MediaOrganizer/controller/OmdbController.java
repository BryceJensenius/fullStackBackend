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
        return omdbService.getMovieDetails(title);
    }
}
