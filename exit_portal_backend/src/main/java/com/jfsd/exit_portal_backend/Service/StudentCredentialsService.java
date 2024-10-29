package com.jfsd.exit_portal_backend.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<StudentCredentials> getStudentCredentials() {
        return studentCredentialsRepository.findAll();
    }

    public ResponseEntity<String> populateCredentialsFromCSV(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a CSV file to upload.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skip the header line if it exists
            String line = reader.readLine();
            
            List<StudentCredentials> credentials = new ArrayList<>();
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                
                // Validate that we have both studentId and password
                if (fields.length != 2) {
                    return ResponseEntity.badRequest().body("Invalid CSV format. Each line should contain studentId and password.");
                }
                
                String studentId = fields[0].trim();
                String password = fields[1].trim();
                
                // Basic validation
                if (studentId.isEmpty() || password.isEmpty()) {
                    continue; // Skip empty entries
                }
                
                StudentCredentials credential = new StudentCredentials(studentId, password);
                credentials.add(credential);
            }
            
            // Save all valid credentials to the database
            studentCredentialsRepository.saveAll(credentials);
            
            return ResponseEntity.ok("Successfully imported " + credentials.size() + " student credentials.");
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to process the CSV file: " + e.getMessage());
        }
    }
}
