package com.jfsd.exit_portal_backend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.RequestBodies.Login;
import com.jfsd.exit_portal_backend.Service.FrontendService;
@RestController
@RequestMapping("/api/v1/frontend")
public class FrontendController {

    @Autowired
    private FrontendService frontendService;

    @PostMapping("/login")
    public ResponseEntity<List<StudentCategoryProgress>> login(@RequestBody Login request) {
        System.out.println(request.getUniversityid());
        return ResponseEntity.ok(frontendService.getStudentCategoryProgress(request.getUniversityid()));

    }



}
