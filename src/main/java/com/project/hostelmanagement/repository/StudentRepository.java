package com.project.hostelmanagement.repository;

import com.project.hostelmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
