package com.BryceJensenius.MediaOrganizer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BryceJensenius.MediaOrganizer.model.FilterRequest;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.model.User;
import com.BryceJensenius.MediaOrganizer.repository.MediaRepository;
import com.BryceJensenius.MediaOrganizer.repository.UserRepository;

@Service
public class MediaServiceImplementation implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MediaItem saveMedia(MediaItem media, User user) {
        user.addMedia(media);
        media = mediaRepository.save(media);
        userRepository.save(user);
        return media;
    }

    @Override
    public List<MediaItem> getAllMedia(User user, FilterRequest filterRequest) {
        List<MediaItem> mediaList = user.getMediaList();
        mediaList.removeIf(m -> {
            boolean ratingCheck = false;
            if(!filterRequest.getRatingFilter().isEmpty()){ // there is a rating so filter the rating
                if(filterRequest.getRatingFilter().length() == 1){ // whole number so take anything thats rounds to this
                    ratingCheck = !((int)m.getRating() == Integer.parseInt(filterRequest.getRatingFilter()));
                }else{//double so take exact value
                    ratingCheck = !(m.getRating() == Double.parseDouble(filterRequest.getRatingFilter()));
                }
            }

            //always check name filter, if it is empty it will be true anyways
            return ratingCheck || !m.getName().toLowerCase().contains(filterRequest.getNameFilter().toLowerCase());
        });

        filterRequest.sort(mediaList);//sort based on order and type specifications in the filter
        return mediaList;
    }

    @Override
    public MediaItem getMediaById(int id, User user) {
        MediaItem item = mediaRepository.findById(id);
        if(item == null || item.getUser() == null || item.getUser().getId() != user.getId()){
            return null;
        }
        return item; // Only return if the media item belongs to the user
    }

    @Override
    public void deleteById(int id, User user){
        MediaItem item = mediaRepository.findById(id);
        if(item == null || item.getUser() == null || item.getUser().getId() != user.getId()){
            return;
        }
        mediaRepository.deleteById(id); // Only delete if the media item belongs to the user
    }
}
