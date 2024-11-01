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

    @Autowired
    private CoursesRepository coursesRepository;

    @Transactional
    public void calculateAndUpdateProgress() {
        // Fetch all grades, students, categories, and courses in one call
        List<StudentGrade> allGrades = studentGradeRepository.findAll();
        List<Categories> allCategories = categoriesRepository.findAll();
        Map<String, List<Courses>> coursesByCategory = coursesRepository.findAll().stream()
                .collect(Collectors.groupingBy(course -> course.getCategory()));
    
        // Group grades by student (universityId)
        Map<String, List<StudentGrade>> gradesByStudent = allGrades.stream()
                .filter(grade -> grade.getUniversityId() != null) // Ensure no null universityId
                .collect(Collectors.groupingBy(StudentGrade::getUniversityId));
    
        // Process each student
        for (Map.Entry<String, List<StudentGrade>> entry : gradesByStudent.entrySet()) {
            String universityId = entry.getKey();
            List<StudentGrade> studentGrades = entry.getValue();
    
            if (studentGrades.isEmpty()) continue;
    
            // Delete existing progress records for this student
            progressRepository.deleteByUniversityId(universityId);
    
            String studentName = studentGrades.get(0).getStudentName();
    
            // Process each category
            for (Categories category : allCategories) {
                List<Courses> categoryCourses = coursesByCategory.getOrDefault(category.getCategoryName(), Collections.emptyList());
                Set<String> categoryCourseCodes = categoryCourses.stream()
                        .map(Courses::getCourseCode)
                        .collect(Collectors.toSet());
    
                // Filter student's completed courses within the category
                List<StudentGrade> completedCourses = studentGrades.stream()
                        .filter(grade -> categoryCourseCodes.contains(grade.getCourseCode()))
                        .filter(grade -> "P".equals(grade.getPromotion())) // Only include if promotion is "P"
                        .collect(Collectors.toList());
    
                // Calculate completed course count and credits sum
                int completedCourseCount = completedCourses.size();
                double completedCreditsSum = completedCourses.stream()
                        .mapToDouble(StudentGrade::getCredits)
                        .sum();
    
                // Create and save progress record
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
