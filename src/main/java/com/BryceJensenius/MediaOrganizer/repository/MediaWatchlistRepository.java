package com.BryceJensenius.MediaOrganizer.repository;

import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaWatchlistRepository extends JpaRepository<MediaWatchItem, Integer> {
    public MediaWatchItem findById(int id);
}