package com.jfsd.exit_portal_backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfsd.exit_portal_backend.RequestBodies.CategoryCoursesDTO;
import com.jfsd.exit_portal_backend.RequestBodies.InvalidPasswordException;
import com.jfsd.exit_portal_backend.RequestBodies.StudentCourseReportDTO;
import com.jfsd.exit_portal_backend.RequestBodies.UserNotFoundException;
import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.Model.StudentGrade;
import com.jfsd.exit_portal_backend.Model.Courses;
import com.jfsd.exit_portal_backend.Model.StudentCredentials;
import com.jfsd.exit_portal_backend.Repository.StudentCategoryProgressRepository;
import com.jfsd.exit_portal_backend.Repository.StudentCredentialsRepository;
import com.jfsd.exit_portal_backend.Repository.StudentGradeRepository;
import com.jfsd.exit_portal_backend.Repository.CoursesRepository;
import com.jfsd.exit_portal_backend.RequestBodies.Student;

@Service
public class FrontendService {

    @Autowired
    private StudentCategoryProgressRepository studentCategoryProgressRepository;

    @Autowired
    private StudentGradeRepository studentGradeRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private StudentCredentialsRepository studentCredentialsRepository;

    public List<StudentCategoryProgress> getStudentCategoryProgress(String request) {
        return studentCategoryProgressRepository.findByUniversityId(request);
    }

    // Updated method to check both universityId and password
    public Student authenticateStudent(String studentId, String password) throws Exception {
        StudentCredentials studentCredentials = studentCredentialsRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    
        if (!studentCredentials.getPassword().equals(password)) {
            throw new InvalidPasswordException("Incorrect password.");
        }
    
        Student studentData = new Student();
        studentData.setUniversityid(studentCredentials.getStudentId());
        return studentData;
    }
    







    
    public List<StudentGrade> getCoursesByCategory(String universityId, String category) {
        return studentGradeRepository.findByUniversityIdAndCategory(universityId, category);
    }

    public List<Courses> getAllCoursesByCategory(String categoryName) {
        return coursesRepository.findAllByCategory(categoryName);
    }

    public StudentCourseReportDTO generateStudentReport(String universityId) {
        // Retrieve all category progress for the student
        List<StudentCategoryProgress> categoryProgress = studentCategoryProgressRepository.findByUniversityId(universityId);
        
        // Check if data exists
        if (categoryProgress == null || categoryProgress.isEmpty()) {
            System.out.println("No data found for universityId: " + universityId);
            return null;
        }
        
        // Initialize the main DTO
        StudentCourseReportDTO reportDTO = new StudentCourseReportDTO();
        reportDTO.setStudentId(universityId);
        reportDTO.setStudentName(categoryProgress.get(0).getStudentName());
    
        // Initialize variables for totals
        List<CategoryCoursesDTO> categoryDTOs = new ArrayList<>();
        int totalCourses = 0;
        double totalCredits = 0.0;
    
        for (StudentCategoryProgress progress : categoryProgress) {
            CategoryCoursesDTO categoryDTO = new CategoryCoursesDTO();
            categoryDTO.setCategoryName(progress.getCategoryName());
            categoryDTO.setMinRequiredCourses(progress.getMinRequiredCourses());
            categoryDTO.setRegisteredCourses(progress.getCompletedCourses());
            categoryDTO.setMinRequiredCredits(progress.getMinRequiredCredits());
            categoryDTO.setRegisteredCredits(progress.getCompletedCredits());
    
            // Get only completed courses for this category
            List<StudentGrade> completedCourses = getCoursesByCategory(universityId, progress.getCategoryName());
            
            if (completedCourses == null || completedCourses.isEmpty()) {
                System.out.println("No completed courses found for category: " + progress.getCategoryName());
            } else {
                categoryDTO.setCourses(completedCourses); // Assuming CategoryCoursesDTO can accept StudentGrade list
                System.out.println("Completed courses for category " + progress.getCategoryName() + ": " + completedCourses);
            }
    
            categoryDTOs.add(categoryDTO);
    
            // Add to totals
            totalCourses += progress.getCompletedCourses();
            totalCredits += progress.getCompletedCredits();
        }
    
        // Populate the final report DTO
        reportDTO.setCategories(categoryDTOs);
        reportDTO.setTotalRegisteredCourses(totalCourses);
        reportDTO.setTotalRegisteredCredits(totalCredits);
    
        // Log the final DTO details
        System.out.println("Generated report for student ID: " + universityId);
        System.out.println("Total Registered Courses: " + totalCourses);
        System.out.println("Total Registered Credits: " + totalCredits);
    
        return reportDTO;
    }
}
