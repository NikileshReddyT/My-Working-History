package com.jfsd.exit_portal_backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.Repository.StudentCategoryProgressRepository;

@Service
public class FrontendService {

    @Autowired
    private StudentCategoryProgressRepository studentCategoryProgressRepository;


    public List<StudentCategoryProgress> getStudentCategoryProgress(String request) {
        return studentCategoryProgressRepository.findByUniversityId(request);
    }   
}
