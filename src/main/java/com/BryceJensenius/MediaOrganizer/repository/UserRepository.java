package com.BryceJensenius.MediaOrganizer.repository;

import com.BryceJensenius.MediaOrganizer.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findById(int id);

    public User findByUsernameAndEncPassword(String username, String encPassword);
}