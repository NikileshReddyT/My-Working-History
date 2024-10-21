package com.jfsd.exit_portal_backend.Repository;

import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentCategoryProgressRepository extends JpaRepository<StudentCategoryProgress, Long> {
    List<StudentCategoryProgress> findByUniversityId(String universityId);
    void deleteByUniversityId(String universityId);
}