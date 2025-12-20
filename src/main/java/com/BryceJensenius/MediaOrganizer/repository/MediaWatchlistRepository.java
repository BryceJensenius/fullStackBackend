package com.BryceJensenius.MediaOrganizer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BryceJensenius.MediaOrganizer.model.MediaWatchItem;

@Repository
public interface MediaWatchlistRepository extends JpaRepository<MediaWatchItem, Integer> {
    public MediaWatchItem findById(int id);
    public List<MediaWatchItem> findByUserId(int userId);
}