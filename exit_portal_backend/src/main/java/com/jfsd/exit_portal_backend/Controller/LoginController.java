package com.jfsd.exit_portal_backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfsd.exit_portal_backend.Service.StudentCredentialsService;

// DTO for login request
class LoginRequest {
    private String universityid;
    private String password;

    public String getUniversityid() {
        return universityid;
    }

    public void setUniversityid(String universityid) {
        this.universityid = universityid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private StudentCredentialsService studentCredentialsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid = studentCredentialsService.validateLogin(loginRequest.getUniversityid(), loginRequest.getPassword());

        if (isValid) {
            // You can return a token or a success message
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid Student ID or Password");
        }
    }

    // Additional authentication-related endpoints can be added here
}
