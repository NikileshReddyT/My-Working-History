package com.jfsd.exit_portal_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_category_progress")
public class StudentCategoryProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "university_id", nullable = false)
    private String universityId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "min_required_credits", nullable = false)
    private Double minRequiredCredits;

    @Column(name = "min_required_courses", nullable = false)
    private Integer minRequiredCourses;

    @Column(name = "completed_courses", nullable = false)
    private Integer completedCourses;

    @Column(name = "completed_credits", nullable = false)
    private Double completedCredits;

    // Default constructor
    public StudentCategoryProgress() {}

    // Parameterized constructor
    public StudentCategoryProgress(String universityId, String studentName, String categoryName, 
                                 Double minRequiredCredits, Integer minRequiredCourses, 
                                 Integer completedCourses, Double completedCredits) {
        this.universityId = universityId;
        this.studentName = studentName;
        this.categoryName = categoryName;
        this.minRequiredCredits = minRequiredCredits;
        this.minRequiredCourses = minRequiredCourses;
        this.completedCourses = completedCourses;
        this.completedCredits = completedCredits;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getMinRequiredCredits() {
        return minRequiredCredits;
    }

    public void setMinRequiredCredits(Double minRequiredCredits) {
        this.minRequiredCredits = minRequiredCredits;
    }

    public Integer getMinRequiredCourses() {
        return minRequiredCourses;
    }

    public void setMinRequiredCourses(Integer minRequiredCourses) {
        this.minRequiredCourses = minRequiredCourses;
    }

    public Integer getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(Integer completedCourses) {
        this.completedCourses = completedCourses;
    }

    public Double getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(Double completedCredits) {
        this.completedCredits = completedCredits;
    }

    @Override
    public String toString() {
        return "StudentCategoryProgress{" +
                "id=" + id +
                ", universityId='" + universityId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", minRequiredCredits=" + minRequiredCredits +
                ", minRequiredCourses=" + minRequiredCourses +
                ", completedCourses=" + completedCourses +
                ", completedCredits=" + completedCredits +
                '}';
    }
}