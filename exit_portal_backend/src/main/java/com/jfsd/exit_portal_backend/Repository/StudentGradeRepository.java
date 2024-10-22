package com.jfsd.exit_portal_backend.Repository;

import com.jfsd.exit_portal_backend.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentGradeRepository extends JpaRepository<StudentGrade, Long> {
    List<StudentGrade> findByUniversityId(String universityId);
    List<StudentGrade> findByUniversityIdAndCategory(String universityId, String category);
}