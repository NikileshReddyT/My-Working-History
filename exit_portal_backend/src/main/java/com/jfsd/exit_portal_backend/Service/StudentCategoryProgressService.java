// Service
package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.*;
import com.jfsd.exit_portal_backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentCategoryProgressService {

    @Autowired
    private StudentCategoryProgressRepository progressRepository;
    
    @Autowired
    private StudentGradeRepository studentGradeRepository;
    
    @Autowired
    private CategoriesRepository categoriesRepository;
    
    // @Autowired
    // private CoursesRepository coursesRepository;

    @Transactional
    public void calculateAndUpdateProgress() {
        // Get all unique students
        List<StudentGrade> allGrades = studentGradeRepository.findAll();
        Set<String> uniqueStudents = allGrades.stream()
                .map(StudentGrade::getUniversityId)
                .collect(Collectors.toSet());

        // Get all categories
        List<Categories> allCategories = categoriesRepository.findAll();

        // Process each student
        for (String universityId : uniqueStudents) {
            // Get student's grades
            List<StudentGrade> studentGrades = allGrades.stream()
                    .filter(grade -> grade.getUniversityId().equals(universityId))
                    .collect(Collectors.toList());

            if (studentGrades.isEmpty()) continue;

            // Delete existing progress records for this student
            progressRepository.deleteByUniversityId(universityId);

            String studentName = studentGrades.get(0).getStudentName();

            // Process each category
            for (Categories category : allCategories) {
                // Get courses in this category
                List<Courses> categoryCourses = category.getCourses();
                Set<String> categoryCourseCodes = categoryCourses.stream()
                        .map(Courses::getCourseCode)
                        .collect(Collectors.toSet());

                // Calculate completed courses and credits for this category
                List<StudentGrade> completedCourses = studentGrades.stream()
                        .filter(grade -> categoryCourseCodes.contains(grade.getCourseCode()))
                        .filter(grade -> !grade.getGrade().equals("F")) // Exclude failed courses
                        .collect(Collectors.toList());

                int completedCourseCount = completedCourses.size();
                double completedCreditsSum = completedCourses.stream()
                        .mapToDouble(StudentGrade::getCredits)
                        .sum();

                // Create progress record
                StudentCategoryProgress progress = new StudentCategoryProgress(
                    universityId,
                    studentName,
                    category.getCategoryName(),
                    category.getMinCredits(),
                    category.getMinCourses(),
                    completedCourseCount,
                    completedCreditsSum
                );

                progressRepository.save(progress);
            }
        }
    }

    public List<StudentCategoryProgress> getStudentProgress(String universityId) {
        return progressRepository.findByUniversityId(universityId);
    }

    public List<StudentCategoryProgress> getAllProgress() {
        return progressRepository.findAll();
    }
}
