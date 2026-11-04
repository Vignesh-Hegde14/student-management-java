package com.vignesh.studentmanagement.repository;

import com.vignesh.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
}
