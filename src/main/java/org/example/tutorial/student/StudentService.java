package org.example.tutorial.student;

import org.springframework.stereotype.Service;

import java.util.List;

public interface StudentService {
    public List<Student> getAllStudents();
    void addNewStudent(Student student);
    void deleteStudent(Long studentId);
    void updateStudent(Long studentId, String newName, String newEmail);
}
