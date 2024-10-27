package com.jfsd.exit_portal_backend.RequestBodies;

import java.util.List;

// Main response DTO containing student info and all categories
public class StudentCourseReportDTO {
    private String studentId;
    private String studentName;
    private List<CategoryCoursesDTO> categories;
    private int totalRegisteredCourses;
    private double totalRegisteredCredits;

    // Constructors
    public StudentCourseReportDTO() {}

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<CategoryCoursesDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryCoursesDTO> categories) {
        this.categories = categories;
    }

    public int getTotalRegisteredCourses() {
        return totalRegisteredCourses;
    }

    public void setTotalRegisteredCourses(int totalRegisteredCourses) {
        this.totalRegisteredCourses = totalRegisteredCourses;
    }

    public double getTotalRegisteredCredits() {
        return totalRegisteredCredits;
    }

    public void setTotalRegisteredCredits(double totalRegisteredCredits) {
        this.totalRegisteredCredits = totalRegisteredCredits;
    }
}
