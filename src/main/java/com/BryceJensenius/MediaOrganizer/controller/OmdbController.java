package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.model.MovieDetails;
import com.BryceJensenius.MediaOrganizer.service.OmdbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/omdb")
@CrossOrigin
public class OmdbController {

    @Autowired
    private OmdbService omdbService;

    /*
        get the list of titles most similar to the inputted one
        shortened to 4 guesses
     */
    @GetMapping("/getTitles/{title}")
    public String[] getMovie(@PathVariable String title) {
        String[] guessList = omdbService.getGuessNames(title);
        return Arrays.copyOfRange(guessList, 0, Math.min(4, guessList.length));//shorten the list to 4 elements
    }

    /*
        get the specific API details on a movie
        JsonObject from the google whatever it not supported my spring so
        we convert to the supported one
     */
    @GetMapping("/getFullInfo/{title}")
    public String[] getFullInfo(@PathVariable String title) {
        JsonObject jsonObject = omdbService.getMovieDetails(title);
        if (jsonObject == null) {
            return new String[]{};
        }
        return extractValues(jsonObject);
    }

    public static String[] extractValues(JsonObject jsonObject) {
        List<String> keyValuePairs = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            JsonElement element = entry.getValue();

            String value = "";
            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();

                // Check if the element is a string or number
                if (primitive.isString()) {
                    value = primitive.getAsString();
                } else if (primitive.isNumber()) {
                    value = primitive.getAsNumber().toString();
                }
            } else if (element.isJsonArray()) {
                // Handle JSON arrays if needed
                value = element.getAsJsonArray().toString();
            } else if (element.isJsonObject()) {
                // Handle nested objects if needed
                value = element.getAsJsonObject().toString();
            }
            keyValuePairs.add(key + ": " + value);
        }

        return keyValuePairs.toArray(new String[0]);
    }
}
