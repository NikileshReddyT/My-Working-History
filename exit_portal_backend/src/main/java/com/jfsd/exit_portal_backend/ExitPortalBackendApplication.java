package com.jfsd.exit_portal_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;



@SpringBootApplication
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5173"})
public class ExitPortalBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExitPortalBackendApplication.class, args);
	}

}
