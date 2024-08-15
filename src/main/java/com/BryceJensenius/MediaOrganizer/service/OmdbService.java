package com.BryceJensenius.MediaOrganizer.service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class OmdbService {

    private final String API_KEY = "d8879d";
    private final String OMDB_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";

    /*
        Get a Movie's Details from it's title
     */
    public JsonObject getMovieDetails(String title) {
        String url = OMDB_URL + "t=" + title.replace(" ", "+");
        RestTemplate restTemplate = new RestTemplate(); //rest template requests can throw errors, add handling later
        String jsonResponse = restTemplate.getForObject(url, String.class);

        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        return jsonObject;
    }
}
