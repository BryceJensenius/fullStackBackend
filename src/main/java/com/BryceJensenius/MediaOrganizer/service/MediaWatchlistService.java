package com.BryceJensenius.MediaOrganizer.service;

import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;
import com.BryceJensenius.MediaOrganizer.repository.MediaRepository;
import com.BryceJensenius.MediaOrganizer.repository.MediaWatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaWatchlistService {

    @Autowired
    private MediaWatchlistRepository mediaWatchlistRepository;

    public MediaWatchItem add(MediaWatchItem item) {
        return mediaWatchlistRepository.save(item);
    }

    public List<MediaWatchItem> getAllMedia() {
        return mediaWatchlistRepository.findAll();
    }

    public MediaWatchItem getMediaById(int id) {
        return mediaWatchlistRepository.findById(id);
    }

    public void delete(int id){
        mediaWatchlistRepository.deleteById(id);
    }
}
