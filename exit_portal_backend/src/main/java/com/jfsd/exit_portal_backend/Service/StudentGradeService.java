package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.StudentGrade;
import com.jfsd.exit_portal_backend.Repository.StudentGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentGradeService {

    @Autowired
    private StudentGradeRepository studentGradeRepository;

    // Upload CSV file
    public List<StudentGrade> uploadCSV(MultipartFile file) throws IOException {
        List<StudentGrade> studentGrades = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Skip the header line
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                
                String[] values = line.split(",");
                StudentGrade grade = new StudentGrade();
                
                // Set values if they exist
                if (values.length > 0) grade.setUniversityId(values[0].trim());
                if (values.length > 1) grade.setStudentName(values[1].trim());
                if (values.length > 2) grade.setCourseCode(values[2].trim());
                if (values.length > 3) grade.setCourseName(values[3].trim());
                if (values.length > 4) grade.setGrade(values[4].trim());
                if (values.length > 5) {
                    try {
                        grade.setGradePoint(Double.parseDouble(values[5].trim()));
                    } catch (NumberFormatException e) {
                        grade.setGradePoint(0.0);
                    }
                }
                if (values.length > 6) {
                    try {
                        grade.setCredits(Double.parseDouble(values[6].trim()));
                    } catch (NumberFormatException e) {
                        grade.setCredits(0.0);
                    }
                }
                if (values.length > 7) grade.setPromotion(values[7].trim());
                if (values.length > 8) grade.setCategory(values[8].trim());
                
                studentGrades.add(grade);
            }
        }
        
        return studentGradeRepository.saveAll(studentGrades);
    }

    // Get all grades
    public List<StudentGrade> getAllGrades() {
        return studentGradeRepository.findAll();
    }

    // Get grades by university ID
    public List<StudentGrade> getGradesByUniversityId(String universityId) {
        return studentGradeRepository.findByUniversityId(universityId);
    }

    // Get grade by ID
    public Optional<StudentGrade> getGradeById(Long id) {
        return studentGradeRepository.findById(id);
    }

    // Save a single grade
    public StudentGrade saveGrade(StudentGrade grade) {
        return studentGradeRepository.save(grade);
    }

    // Delete a grade
    public void deleteGrade(Long id) {
        studentGradeRepository.deleteById(id);
    }

    // Update a grade
    public StudentGrade updateGrade(Long id, StudentGrade gradeDetails) {
        Optional<StudentGrade> grade = studentGradeRepository.findById(id);
        if (grade.isPresent()) {
            StudentGrade existingGrade = grade.get();
            existingGrade.setUniversityId(gradeDetails.getUniversityId());
            existingGrade.setStudentName(gradeDetails.getStudentName());
            existingGrade.setCourseCode(gradeDetails.getCourseCode());
            existingGrade.setCourseName(gradeDetails.getCourseName());
            existingGrade.setGrade(gradeDetails.getGrade());
            existingGrade.setGradePoint(gradeDetails.getGradePoint());
            existingGrade.setCredits(gradeDetails.getCredits());
            existingGrade.setPromotion(gradeDetails.getPromotion());
            existingGrade.setCategory(gradeDetails.getCategory());
            return studentGradeRepository.save(existingGrade);
        }
        return null;
    }
}