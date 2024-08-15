package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.service.OmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/omdb")
public class OmdbController {

    @Autowired
    private OmdbService omdbService;

    @GetMapping("/movie")
    public JsonObject getMovie(@RequestParam String title) {
        return omdbService.getMovieDetails(title);
    }
}
