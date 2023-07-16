package com.project.hostelmanagement.service;

import com.project.hostelmanagement.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> fetchStudents();

    StudentDto saveStudent(StudentDto studentDto);

    StudentDto deleteStudent(String studentId);
}
