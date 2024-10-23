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
                    firstLine = false; // Skip the header
                    continue;
                }
    
                // Parse the line considering quoted commas
                String[] values = parseCsvLine(line);
    
                StudentGrade grade = new StudentGrade();
    
                // Set values if they exist
                if (values.length > 0) grade.setUniversityId(values[0].trim());
                if (values.length > 1) grade.setStudentName(values[1].trim());
                if (values.length > 2) grade.setCourseCode(values[2].trim());
                if (values.length > 3) grade.setCourseName(values[3].trim());
    
                // Clean up the grade field (5th column)
                if (values.length > 4) {
                    String gradeValue = values[4].trim();
                    int indexOfParenthesis = gradeValue.indexOf('(');
                    if (indexOfParenthesis != -1) {
                        gradeValue = gradeValue.substring(0, indexOfParenthesis).trim(); // Remove any grades in parentheses
                    }
                    gradeValue = gradeValue.replace("\"", ""); // Remove leading/trailing quotes if present
                    grade.setGrade(gradeValue);
                }
    
                // Set grade point, credits, promotion, and category
                if (values.length > 5) {
                    String gradePointValue = values[5].trim();
                    try {
                        grade.setGradePoint(Double.parseDouble(gradePointValue)); // Parse grade point
                    } catch (NumberFormatException e) {
                        grade.setGradePoint(0.0); // Default to 0.0 if parsing fails
                    }
                }
    
                if (values.length > 6) {
                    String creditsValue = values[6].trim();
                    try {
                        grade.setCredits(Double.parseDouble(creditsValue)); // Parse credits
                    } catch (NumberFormatException e) {
                        grade.setCredits(0.0); // Default to 0.0 if parsing fails
                    }
                }
    
                if (values.length > 7) grade.setPromotion(values[7].trim());
                if (values.length > 8) grade.setCategory(values[8].trim());
    
                studentGrades.add(grade); // Add the populated StudentGrade object to the list
            }
        }
    
        return studentGradeRepository.saveAll(studentGrades); // Save all student grades to the repository
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




    private String[] parseCsvLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;
    
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString().trim()); // Add current value and reset
                currentValue.setLength(0);
            } else {
                currentValue.append(c); // Append character to current value
            }
        }
        // Add the last value if present
        if (currentValue.length() > 0) {
            values.add(currentValue.toString().trim());
        }
    
        return values.toArray(new String[0]);
    }





}