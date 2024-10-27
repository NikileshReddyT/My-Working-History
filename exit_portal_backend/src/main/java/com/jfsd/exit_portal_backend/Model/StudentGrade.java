package com.jfsd.exit_portal_backend.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_grades")
public class StudentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;
    
    @Column(name = "university_id")
    private String universityId;
    
    @Column(name = "student_name")
    private String studentName;
    
    private String status;
    
    @Column(name = "course_code")
    private String courseCode;
    
    @Column(name = "course_name")
    private String courseName;
    
    private String grade;
    
    @Column(name = "grade_point")
    private Double gradePoint;
    
    private Double credits;
    
    private String promotion;
    
    @Column(nullable = false)
    private String category;
    
    @Column(name = "academic_year")
    private String year;
    
    @Column(name = "semester")
    private String semester;

    // Getters and Setters
    public Long getSno() { return sno; }
    public void setSno(Long sno) { this.sno = sno; }

    public String getUniversityId() { return universityId; }
    public void setUniversityId(String universityId) { this.universityId = universityId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Double getGradePoint() { return gradePoint; }
    public void setGradePoint(Double gradePoint) { this.gradePoint = gradePoint; }

    public Double getCredits() { return credits; }
    public void setCredits(Double credits) { this.credits = credits; }

    public String getPromotion() { return promotion; }
    public void setPromotion(String promotion) { this.promotion = promotion; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
}
