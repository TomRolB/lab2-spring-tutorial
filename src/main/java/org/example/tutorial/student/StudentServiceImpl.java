package org.example.tutorial.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalArgumentException("Email taken");
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Override
    @Transactional
    public void updateStudent(Long studentId, String newName, String newEmail) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student does not exist");
        }

        Student student = studentRepository.getReferenceById(studentId);

        if (student.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("This is your account's current email");
        }

        if (student.getName().equals(newName)) {
            throw new IllegalArgumentException("This is your account's current name");
        }

        if (studentRepository.findStudentByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException("Email taken");
        }

        student.setName(newName);
        student.setEmail(newEmail);
    }
}
