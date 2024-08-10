package com.BryceJensenius.MediaOrganizer.controller;

import com.BryceJensenius.MediaOrganizer.model.FilterRequest;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mediaItems")
@CrossOrigin
public class MediaController {
    @Autowired
    private MediaService mediaService;

    private String nameFilter = "";
    private String ratingFilter = "";
    private int editId = -1;//id of the element that is being edited, -1 is no element

    @PostMapping("/add")
    public String add(@RequestBody MediaItem media){
        editId = -1;//after submit is hit, you're done editing
        mediaService.saveMedia(media);
        return "New Media Item Was Added";
    }

    @PostMapping("/setFilter")
    public void setFilter(@RequestBody FilterRequest filterRequest) {
        this.nameFilter = filterRequest.getNameFilter(); // Get the filter from the request object
        this.ratingFilter = filterRequest.getRatingFilter();
    }

    @GetMapping("/getById/{id}")
    public Optional<MediaItem> getMediaById(@PathVariable int id){
        editId = id;//element is being edited
        return mediaService.getMediaById(id);
    }

    @GetMapping("/getAll")
    public List<MediaItem> getAllMedia() {
        List<MediaItem> mediaList = mediaService.getAllMedia();//get media list from database
        mediaList.removeIf(m -> {
            boolean ratingCheck = false;
            boolean isEditedElement = editId == m.getId();
            if(!ratingFilter.isEmpty()){//there is a rating so filter the rating
                if(ratingFilter.length() == 1){//whole number so take anything thats rounds to this
                    ratingCheck = !((int)m.getRating() == Integer.parseInt(ratingFilter));
                }else{//double so take exact value
                    ratingCheck = !(m.getRating() == Double.parseDouble(ratingFilter));
                }
            }

            //always check name filter, if it is empty it will be true anyways
            //if it is the element being edited, don't remove
            return !isEditedElement
                    && (ratingCheck || !m.getName().toLowerCase().contains(nameFilter.toLowerCase()));
        });

        return mediaList;
    }
}