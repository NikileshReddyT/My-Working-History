package com.jfsd.exit_portal_backend.Controller;

import com.jfsd.exit_portal_backend.Service.CourseCreditsVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/verification")
@CrossOrigin
public class CourseCreditsVerificationController {

    @Autowired
    private CourseCreditsVerificationService verificationService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCourseCredits(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a CSV file to upload.");
        }

        try {
            List<String> verificationResults = verificationService.verifyCourseCredits(file);
            return ResponseEntity.ok(verificationResults);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to process the CSV file: " + e.getMessage());
        }
    }
}
