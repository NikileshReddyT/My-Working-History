package com.jfsd.exit_portal_backend.Repository;

import com.jfsd.exit_portal_backend.Model.Courses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    // Custom method to find the first course by code
    Optional<Courses> findFirstByCourseCode(String courseCode);
    @Query("SELECT c FROM Courses c WHERE c.category = :categoryName")
    List<Courses> findByCategoryName(@Param("categoryName") String categoryName);}
