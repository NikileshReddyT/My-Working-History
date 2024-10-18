package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.Courses;
import com.jfsd.exit_portal_backend.Repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseCreditsVerificationService {

    @Autowired
    private CoursesRepository coursesRepository;

    public List<String> verifyCourseCredits(MultipartFile file) throws Exception {
        List<String> messages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }

                String[] values = line.split(",");
                if (values.length >= 2) {
                    String courseCode = values[0].trim();
                    double csvCredits;

                    try {
                        csvCredits = Double.parseDouble(values[1].trim());
                    } catch (NumberFormatException e) {
                        messages.add(courseCode + ",false,Invalid credit value in CSV");
                        continue;
                    }

                    Optional<Courses> courseOptional = coursesRepository.findFirstByCourseCode(courseCode);
                    if (courseOptional.isPresent()) {
                        Courses course = courseOptional.get();
                        double dbCredits = course.getCourseCredits();

                        if (Math.abs(csvCredits - dbCredits) < 0.001) { // Using a small epsilon for double comparison
                            messages.add(courseCode + ",true,Credits match");
                        } else {
                            // Update the credits in the database
                            course.setCourseCredits(csvCredits);
                            coursesRepository.save(course);
                            messages.add(courseCode + ",true,Credits updated from " + dbCredits + " to " + csvCredits);
                        }
                    } else {
                        messages.add(courseCode + ",false,Course not found in database");
                    }
                } else {
                    messages.add("Invalid,false,Insufficient data in CSV row");
                }
            }
        } catch (Exception e) {
            throw new Exception("Error processing CSV file: " + e.getMessage());
        }

        return messages;
    }
}
