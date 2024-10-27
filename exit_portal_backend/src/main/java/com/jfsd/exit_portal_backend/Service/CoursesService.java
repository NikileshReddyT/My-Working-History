package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.Courses;
import com.jfsd.exit_portal_backend.Repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public Courses getCourseById(int id) {
        return coursesRepository.findById(id).orElse(null);
    }

    public Courses saveCourse(Courses course) {
        return coursesRepository.save(course);
    }

    @Transactional
    public List<String> populateCoursesFromCSV(MultipartFile file) throws IOException {
        List<String> messages = new ArrayList<>();
        List<Courses> coursesToSave = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = br.readLine(); // Skip the header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (values.length >= 4) {
                    String courseCode = values[0].trim();
                    String courseTitle = values[1].trim().replace("\"", "");
                    double credits;
                    try {
                        credits = Double.parseDouble(values[2].trim());
                    } catch (NumberFormatException e) {
                        messages.add("Invalid credit value for course " + courseCode + ". Skipping.");
                        credits = 0.0;
                        continue;
                    }
                    String categoryName = values[3].trim();

                    Optional<Courses> existingCourse = coursesRepository.findFirstByCourseCode(courseCode);
                    if (existingCourse.isPresent()) {
                        messages.add("Course with code " + courseCode + " already exists. Skipping.");
                        continue;
                    }

                    Courses course = new Courses();
                    course.setCourseCode(courseCode);
                    course.setCourseTitle(courseTitle);
                    course.setCourseCredits(credits);
                    course.setCategory(categoryName);

                    coursesToSave.add(course);
                }
            }

            if (!coursesToSave.isEmpty()) {
                coursesRepository.saveAll(coursesToSave);
                messages.add("Successfully imported " + coursesToSave.size() + " courses.");
            } else {
                messages.add("No new courses to import.");
            }

        } catch (Exception e) {
            messages.add("Error processing CSV file: " + e.getMessage());
            throw e;
        }

        return messages;
    }
}
