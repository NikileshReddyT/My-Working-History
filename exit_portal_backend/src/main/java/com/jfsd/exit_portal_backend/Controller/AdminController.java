package com.jfsd.exit_portal_backend.Controller;

import com.jfsd.exit_portal_backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/consolidation/{universityId}")
    public com.jfsd.exit_portal_backend.RequestBodies.Admin getAdminConsolidationByUniversityId(@PathVariable String universityId) {
        return adminService.getAdminConsolidationByUniversityId(universityId);
    }
}
