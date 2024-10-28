package com.jfsd.exit_portal_backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.exit_portal_backend.Model.Courses;
import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.Model.StudentGrade;
import com.jfsd.exit_portal_backend.RequestBodies.Login;
import com.jfsd.exit_portal_backend.RequestBodies.StudentCourseReportDTO;
import com.jfsd.exit_portal_backend.Service.FrontendService;

@RestController
@RequestMapping("/api/v1/frontend")
public class FrontendController {

    @Autowired
    private FrontendService frontendService;

    @PostMapping("/getdata")
    public ResponseEntity<List<StudentCategoryProgress>> getdata(@RequestBody Login request) {
        System.out.println(request.getUniversityId());
        return ResponseEntity.ok(frontendService.getStudentCategoryProgress(request.getUniversityId()));
    }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody Login loginRequest) {
        // Update this line to use the new service method which requires both universityId and password
        Login login = frontendService.findStudentByUniversityId(loginRequest.getUniversityId(), loginRequest.getPassword());
        if (login != null) {
            return ResponseEntity.ok(login); // Return student data if found
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Return 401 if authentication fails
        }
    }

    @GetMapping("/getcategorydetails/{categoryName}/{studentId}")
    public ResponseEntity<List<StudentGrade>> getCategoryDetails(@PathVariable("categoryName") String categoryName, @PathVariable("studentId") String studentId) {
        System.out.println("Category Name: " + categoryName);
        System.out.println("Student ID: " + studentId);
        List<StudentGrade> courses = frontendService.getCoursesByCategory(studentId, categoryName);
        System.out.println(courses);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/getallcourses/{categoryName}")
    public ResponseEntity<List<Courses>> getAllCourses(@PathVariable("categoryName") String categoryName) {
        List<Courses> allCourses = frontendService.getAllCoursesByCategory(categoryName);
        return new ResponseEntity<>(allCourses, HttpStatus.OK);
    }

    @PostMapping("/generatereport")
    public ResponseEntity<StudentCourseReportDTO> generateStudentReport(@RequestBody Map<String, String> requestBody) {
        String universityId = requestBody.get("universityId");
        if (universityId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        StudentCourseReportDTO reportDTO = frontendService.generateStudentReport(universityId);
        return ResponseEntity.ok(reportDTO);
    }
}
