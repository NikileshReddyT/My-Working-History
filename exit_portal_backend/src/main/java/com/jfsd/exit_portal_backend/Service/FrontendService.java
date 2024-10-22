package com.jfsd.exit_portal_backend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfsd.exit_portal_backend.RequestBodies.Student;
import com.jfsd.exit_portal_backend.Model.StudentCategoryProgress;
import com.jfsd.exit_portal_backend.Model.StudentGrade;
import com.jfsd.exit_portal_backend.Repository.StudentCategoryProgressRepository;
import com.jfsd.exit_portal_backend.Repository.StudentGradeRepository;
@Service
public class FrontendService {

    @Autowired
    private StudentCategoryProgressRepository studentCategoryProgressRepository;

    @Autowired
    private StudentGradeRepository studentGradeRepository;


    public List<StudentCategoryProgress> getStudentCategoryProgress(String request) {
        return studentCategoryProgressRepository.findByUniversityId(request);
    }


    public Student findStudentByUniversityId(String universityId) {
        // Assuming studentRepository interacts with your database
        StudentCategoryProgress studentCategoryProgress = studentCategoryProgressRepository.findFirstByUniversityId(universityId);
        Student student = new Student();
        student.setUniversityid(studentCategoryProgress.getUniversityId());
        student.setName(studentCategoryProgress.getStudentName());
        return student;
    }

      public List<StudentGrade> getCoursesByCategory(String universityId, String category) {
        return studentGradeRepository.findByUniversityIdAndCategory(universityId, category);
    }
}
