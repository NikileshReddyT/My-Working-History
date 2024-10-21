// Controller
package com.jfsd.exit_portal_backend.Controller;

import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.Service.StudentCategoryProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "http://localhost:5500")
public class StudentCategoryProgressController {

    @Autowired
    private StudentCategoryProgressService progressService;

    @GetMapping("/run")
    public ResponseEntity<String> calculateProgress() {
        try {
            progressService.calculateAndUpdateProgress();
            return ResponseEntity.ok("Progress calculation completed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Error calculating progress: " + e.getMessage());
        }
    }

    @GetMapping("/student/{universityId}")
    public ResponseEntity<List<StudentCategoryProgress>> getStudentProgress(
            @PathVariable String universityId) {
        return ResponseEntity.ok(progressService.getStudentProgress(universityId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentCategoryProgress>> getAllProgress() {
        return ResponseEntity.ok(progressService.getAllProgress());
    }
}