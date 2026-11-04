package com.vignesh.studentmanagement.service;

import com.vignesh.studentmanagement.model.Student;
import com.vignesh.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() { return repository.findAll(); }
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return repository.findAll();
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }
    public Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found: " + id));
    }
    public Student save(Student student) { return repository.save(student); }
    public void delete(Long id) { repository.deleteById(id); }
    public long count() { return repository.count(); }
    public long eligibleCount() { return repository.findAll().stream().filter(Student::isEligible).count(); }
}
