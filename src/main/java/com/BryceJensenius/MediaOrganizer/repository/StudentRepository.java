package com.BryceJensenius.MediaOrganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BryceJensenius.MediaOrganizer.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}