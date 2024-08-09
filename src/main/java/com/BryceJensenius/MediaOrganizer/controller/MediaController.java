package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.model.FilterRequest;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mediaItems")
@CrossOrigin
public class MediaController {
    @Autowired
    private MediaService mediaService;

    private String nameFilter = "";
    private String ratingFilter = "";

    @PostMapping("/add")
    public String add(@RequestBody MediaItem media){
        mediaService.saveMedia(media);
        return "New Media Item Was Added";
    }

    @PostMapping("/setFilter")
    public void setFilter(@RequestBody FilterRequest filterRequest) {
        this.nameFilter = filterRequest.getNameFilter(); // Get the filter from the request object
        this.ratingFilter = filterRequest.getRatingFilter();
    }

    @GetMapping("/getAll")
    public List<MediaItem> getAllMedia() {
        List<MediaItem> mediaList = mediaService.getAllMedia();//get media list from database
        mediaList.removeIf(m -> {
            boolean ratingCheck = false;
            if(!ratingFilter.isEmpty()){//there is a rating so filter the rating
                if(ratingFilter.length() == 1){//whole number so take anything thats rounds to this
                    ratingCheck = !((int)m.getRating() == Integer.parseInt(ratingFilter));
                }else{//double so take exact value
                    ratingCheck = !(m.getRating() == Double.parseDouble(ratingFilter));
                }
            }

            //always check name filter, if it is empty it will be true anyways
            return ratingCheck || !m.getName().toLowerCase().contains(nameFilter.toLowerCase());
        });
        return mediaList;
    }
}