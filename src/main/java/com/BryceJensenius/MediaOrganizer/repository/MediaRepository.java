package com.BryceJensenius.MediaOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BryceJensenius.MediaOrganizer.model.MediaItem;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaItem, Integer> {

}