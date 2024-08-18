package com.BryceJensenius.MediaOrganizer.service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

@Service
public class OmdbService {

    private final String API_KEY = "d8879d";
    private final String OMDB_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";

    /*
        Guess a Movie's Title from partially complete title
     */
    public String[] getGuessNames(String title) {
        String url = OMDB_URL + "s=" + title.replace(" ", "+");
        RestTemplate restTemplate = new RestTemplate(); //rest template requests can throw errors, add handling later
        try{
            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            //List is contained in a Search entry, and entries inside should be JSON Array
            if(jsonObject.has("Search") && jsonObject.get("Search").isJsonArray()){
                JsonArray jsonArray = jsonObject.getAsJsonArray("Search");//grab search and get it's list
                List<String> movieNamesList = new ArrayList<>();//list of name guesses

                for(JsonElement media : jsonArray) {//arrays have elements, the elements must be converted to json object
                    JsonObject mediaObject = media.getAsJsonObject();
                    if(mediaObject.has("Title")){//ensure object returned has a title before grabbing it
                        movieNamesList.add(mediaObject.get("Title").getAsString());//convert JSON Title to string
                    }
                }
                return movieNamesList.toArray(new String[0]);//input of array for elements to be stored in, 0 has size chosen internally
            }else {
                return new String[]{"No Results"};
            }
        }catch(NullPointerException e) {
            return new String[]{"Null Error Occurred"};
        }catch(Exception e){
            return new String[]{"API Call Failed"};
        }
    }

    public JsonObject getMovieDetails(String title){
        String url = OMDB_URL + "t=" + title.replace(" ", "+");//search for one with this title
        RestTemplate restTemplate = new RestTemplate(); //rest template requests can throw errors, add handling later
        try{
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return JsonParser.parseString(jsonResponse).getAsJsonObject();
        }catch(Exception e){
            return null;
        }
    }
}
