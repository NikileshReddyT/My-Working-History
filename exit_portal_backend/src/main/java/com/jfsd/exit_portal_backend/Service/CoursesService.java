package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.Categories;
import com.jfsd.exit_portal_backend.Model.Courses;
import com.jfsd.exit_portal_backend.Repository.CategoriesRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

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




        
        
        // Create a cache of category names to Categories objects
        Map<String, Categories> categoryCache = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skip the header line
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (values.length >= 3) {
                    String courseCode = values[0].trim();
                    String courseTitle = values[1].trim().replace("\"", "");
                    String categoryName = values[2].trim();

                    // Check if course already exists
                    Optional<Courses> existingCourse = coursesRepository.findFirstByCourseCode(courseCode);
                    if (existingCourse.isPresent()) {
                        messages.add("Course with code " + courseCode + " already exists. Skipping.");
                        continue;
                    }

                    Courses course = new Courses();
                    course.setCourseCode(courseCode);
                    course.setCourseTitle(courseTitle);
                    course.setCourseCredits(0.0);

                    // Try to find category from cache first
                    Categories category = categoryCache.get(categoryName);
                    if (category == null) {
                        // If not in cache, try to find in database
                        category = categoriesRepository.findByCategoryNameIgnoreCase(categoryName);
                        if (category != null) {
                            // Add to cache for future use
                            categoryCache.put(categoryName, category);
                        }
                    }

                    // Set category (might be null if not found)
                    course.setCategory(category);
                    if (category == null) {
                        messages.add("Category '" + categoryName + "' not found for course " + courseCode);
                    }

                    coursesToSave.add(course);
                }
            }

            // Save all courses in batch
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

    @Transactional
    public List<String> addCourseCredits(MultipartFile file) throws IOException {
        List<String> messages = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            // Skip the header line
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String courseCode = values[0].trim();
                    double credits;
                    try {
                        credits = Double.parseDouble(values[1].trim());
                    } catch (NumberFormatException e) {
                        messages.add("Invalid credit value for course " + courseCode + ". Skipping.");
                        continue;
                    }

                    Optional<Courses> courseOptional = coursesRepository.findFirstByCourseCode(courseCode);
                    if (courseOptional.isPresent()) {
                        Courses course = courseOptional.get();
                        course.setCourseCredits(credits);
                        coursesRepository.save(course);
                        messages.add("Updated credits for course " + courseCode + " to " + credits);
                    } else {
                        messages.add("Course with code " + courseCode + " not found. Skipping.");
                    }
                }
            }
        } catch (Exception e) {
            messages.add("Error processing CSV file: " + e.getMessage());
            throw e;
        }

        return messages;
    }
}
