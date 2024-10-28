package com.jfsd.exit_portal_backend.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfsd.exit_portal_backend.Model.StudentCredentials;
import com.jfsd.exit_portal_backend.Repository.StudentCredentialsRepository;
import com.jfsd.exit_portal_backend.Repository.StudentGradeRepository;

@Service
public class StudentCredentialsService {

    @Autowired
    private StudentGradeRepository studentGradeRepository;

    @Autowired
    private StudentCredentialsRepository studentCredentialsRepository;

    // Method to generate and save unique student credentials
    public List<StudentCredentials> generateAndSaveUniqueStudentCredentials() {
        // Retrieve all unique student IDs from the StudentGrade repository
        List<String> studentIds = studentGradeRepository.findAllUniqueStudentIds();
        
        // Use a Set to handle unique student IDs
        Set<String> uniqueStudentIds = new HashSet<>(studentIds);
        
        List<StudentCredentials> credentials = new ArrayList<>();
        Random random = new Random();

        for (String studentId : uniqueStudentIds) {
            // Generate a 6-digit random password
            String password = String.format("%06d", random.nextInt(999999));
            StudentCredentials credential = new StudentCredentials(studentId, password);
            credentials.add(credential);
        }

        // Save all credentials to the StudentCredentials table
        return studentCredentialsRepository.saveAll(credentials);
    }

    // Method to find student credentials by studentId
    public Optional<StudentCredentials> findByStudentId(String studentId) {
        return studentCredentialsRepository.findByStudentId(studentId);
    }
}
