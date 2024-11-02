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
        // Upload CSV file
        public List<String> uploadCSV(MultipartFile file) throws IOException {
            List<String> messages = new ArrayList<>();
            List<StudentGrade> studentGradesToSave = new ArrayList<>();
    
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                boolean firstLine = true;
    
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false; // Skip the header
                        continue;
                    }
    
                    String[] values = parseCsvLine(line);
                    if (values.length < 5) continue; // Ensure required fields are present
    
                    String universityId = values[0].trim();
                    String courseCode = values[3].trim();
    
                    // Check if this record already exists based on universityId and courseCode
                    boolean exists = studentGradeRepository.existsByUniversityIdAndCourseCode(universityId, courseCode);
                    if (exists) {
                        messages.add("Grade record for University ID " + universityId + " and Course Code " + courseCode + " already exists. Skipping.");
                        System.out.println("Grade record for University ID " + universityId + " and Course Code " + courseCode + " already exists. Skipping.");
                        continue;
                    }
    
                    StudentGrade grade = new StudentGrade();
                    grade.setUniversityId(universityId);
                    grade.setStudentName(values[1].trim());
                    grade.setStatus(values[2].trim());
                    grade.setCourseCode(courseCode);
                    grade.setCourseName(values[4].trim());
    
                    // Parsing and setting additional fields as per original code...
                    if (values.length > 5) {
                        String gradeValue = values[5].trim();
                        int indexOfParenthesis = gradeValue.indexOf('(');
                        if (indexOfParenthesis != -1) {
                            gradeValue = gradeValue.substring(0, indexOfParenthesis).trim();
                        }
                        grade.setGrade(gradeValue.replace("\"", ""));
                    }
    
                    if (values.length > 6) {
                        try {
                            grade.setGradePoint(Double.parseDouble(values[6].trim()));
                        } catch (NumberFormatException e) {
                            grade.setGradePoint(0.0);
                        }
                    }
    
                    if (values.length > 7) {
                        try {
                            grade.setCredits(Double.parseDouble(values[7].trim()));
                        } catch (NumberFormatException e) {
                            grade.setCredits(0.0);
                        }
                    }
    
                    if (values.length > 8) grade.setPromotion(values[8].trim());
                    if (values.length > 9) grade.setYear(values[9].trim());
                    if (values.length > 10) grade.setSemester(values[10].trim());
                    if (values.length > 11) grade.setCategory(values[11].trim());
    
                    studentGradesToSave.add(grade);
                }
            }
    
            // Save new grades and return messages
            if (!studentGradesToSave.isEmpty()) {
                studentGradeRepository.saveAll(studentGradesToSave);
                messages.add("Successfully imported " + studentGradesToSave.size() + " new grade records.");
            } else {
                messages.add("No new grade records to import.");
            }
    
            return messages;
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
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString().trim());
                currentValue.setLength(0);
            } else {
                currentValue.append(c);
            }
        }
        if (currentValue.length() > 0) {
            values.add(currentValue.toString().trim());
        }

        return values.toArray(new String[0]);
    }





}