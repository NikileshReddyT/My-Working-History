package com.jfsd.exit_portal_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfsd.exit_portal_backend.Model.StudentCredentials;

@Repository
public interface StudentCredentialsRepository extends JpaRepository<StudentCredentials, Long> {
    Optional<StudentCredentials> findByStudentId(String studentId);  // Corrected method definition
}
