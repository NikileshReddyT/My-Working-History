package com.jfsd.exit_portal_backend.Service;

import com.jfsd.exit_portal_backend.Model.Categories;
import com.jfsd.exit_portal_backend.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    // ... other methods ...

    @Transactional
    public void populateCategoriesFromCSV(MultipartFile file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the header
                }
                String[] values = line.split(",");
                if (values.length >= 4) {
                    Categories category = new Categories();
                    category.setCategoryName(values[1].trim());
                    category.setMinCourses(Integer.parseInt(values[2].trim()));
                    category.setMinCredits(Double.parseDouble(values[3].trim()));
                    categoriesRepository.save(category);
                }
            }
        }
    }

    public void addCourseCredits(MultipartFile file) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCourseCredits'");
    }
}