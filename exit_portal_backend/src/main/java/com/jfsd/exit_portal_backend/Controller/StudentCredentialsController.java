package com.jfsd.exit_portal_backend.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.exit_portal_backend.Model.StudentCredentials;
import com.jfsd.exit_portal_backend.Service.StudentCredentialsService;

@RestController
@RequestMapping("/api/credentials")
public class StudentCredentialsController {

    @Autowired
    private StudentCredentialsService studentCredentialsService;

    // Endpoint to generate credentials
    @GetMapping("/generate")
    public List<StudentCredentials> generateCredentials() {
        return studentCredentialsService.generateAndSaveUniqueStudentCredentials();
    }

    // New login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentCredentials loginRequest) {
        // Fetch the student credentials using the provided studentId
        Optional<StudentCredentials> studentCredentialsOpt = studentCredentialsService.findByStudentId(loginRequest.getStudentId());

        if (studentCredentialsOpt.isPresent()) {
            StudentCredentials studentCredentials = studentCredentialsOpt.get();

            // Check if the provided password matches the stored password
            if (studentCredentials.getPassword().equals(loginRequest.getPassword())) {  // Use hashing for production
                return ResponseEntity.ok("Login successful");  // Send a success response
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Student ID or Password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Student ID or Password");
        }
    }
}
