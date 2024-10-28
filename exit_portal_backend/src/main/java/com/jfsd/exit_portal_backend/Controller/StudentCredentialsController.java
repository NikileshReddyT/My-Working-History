package com.jfsd.exit_portal_backend.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.exit_portal_backend.Model.StudentCredentials;
import com.jfsd.exit_portal_backend.Service.StudentCredentialsService;

@RestController
@RequestMapping("/api/credentials")
public class StudentCredentialsController {

    @Autowired
    private StudentCredentialsService studentCredentialsService;

    // Endpoint to generate credentials
    @GetMapping("/generate")
    public List<StudentCredentials> generateCredentials() {
        return studentCredentialsService.generateAndSaveUniqueStudentCredentials();
    }
}
