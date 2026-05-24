package com.BryceJensenius.MediaOrganizer.service;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class OmdbService {

    private static final Logger logger = LoggerFactory.getLogger(OmdbService.class);

    private final String API_KEY;
    private final String OMDB_URL;

    public OmdbService(@Value("${OMDB_API_KEY}") String apiKey) {
        API_KEY = apiKey;
        OMDB_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&";
    }

    /*
        Guess a Movie's Title from partially complete title
     */
    public String[] getGuessNames(String title) {
        String url = OMDB_URL + "s=" + title.replace(" ", "+");
        RestTemplate restTemplate = new RestTemplate(); //rest template requests can throw errors, add handling later
            logger.debug("Fetching OMDb title suggestions for title='{}'", title);
        try{
            String jsonResponse = restTemplate.getForObject(url, String.class);
                if (jsonResponse == null) {
                    logger.warn("OMDb returned a null response body for title suggestions, title='{}'", title);
                    return new String[]{"Null Error Occurred"};
                }

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
                logger.info("OMDb returned {} title suggestions for title='{}'", movieNamesList.size(), title);
                return movieNamesList.toArray(new String[0]);//input of array for elements to be stored in, 0 has size chosen internally
            }else {
                logger.info("OMDb returned no title suggestions for title='{}'", title);
                return new String[]{"No Results"};
            }
        }catch(NullPointerException e) {
            logger.error("Null data encountered while parsing OMDb title suggestions for title='{}'", title, e);
            return new String[]{"Null Error Occurred"};
        }catch(Exception e){
            logger.error("OMDb title suggestion request failed for title='{}'", title, e);
            return new String[]{"API Call Failed"};
        }
    }

    public JsonObject getMovieDetails(String title){
        String url = OMDB_URL + "t=" + title.replace(" ", "+");//search for one with this title
        RestTemplate restTemplate = new RestTemplate(); //rest template requests can throw errors, add handling later
        logger.debug("Fetching OMDb movie details for title='{}'", title);
        try{
            String jsonResponse = restTemplate.getForObject(url, String.class);
            if (jsonResponse == null) {
                logger.warn("OMDb returned a null response body for movie details, title='{}'", title);
                return null;
            }

            logger.info("OMDb returned movie details for title='{}'", title);
            return JsonParser.parseString(jsonResponse).getAsJsonObject();
        }catch(Exception e){
            logger.error("OMDb movie details request failed for title='{}'", title, e);
            return null;
        }
    }
}
