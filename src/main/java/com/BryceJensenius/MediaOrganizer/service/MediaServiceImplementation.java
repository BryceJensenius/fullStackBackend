package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaServiceImplementation implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public MediaItem saveMedia(MediaItem media) {
        return mediaRepository.save(media);
    }

    @Override
    public List<MediaItem> getAllMedia() {
        return mediaRepository.findAll();
    }
}
