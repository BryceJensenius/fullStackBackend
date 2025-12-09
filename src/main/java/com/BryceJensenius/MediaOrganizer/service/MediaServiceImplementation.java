package com.BryceJensenius.MediaOrganizer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<MediaItem> getAllMedia(User user) {
        return user.getMediaList();
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
