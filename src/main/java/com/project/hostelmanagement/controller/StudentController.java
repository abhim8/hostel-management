package com.project.hostelmanagement.controller;

import com.project.hostelmanagement.dto.StudentDto;
import com.project.hostelmanagement.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(produces = "application/json")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/v1/students")
    private List<StudentDto> fetchAllStudents(){
        log.info("fetching all students from db");
        List<StudentDto> studentDtoList = studentService.fetchStudents();
        log.info("fetched students");
        return studentDtoList;
    }

    @PostMapping("/v1/student")
    private StudentDto saveStudent(
            @RequestBody StudentDto studentDto
    ){
        log.info("saving details of a student");
        StudentDto savedStudentDetails = studentService.saveStudent(studentDto);
        log.info("saved student details");
        return savedStudentDetails;
    }

    @DeleteMapping("/v1/student")
    private StudentDto deleteStudent(
            @RequestParam String studentId
    ){
        log.info("saving details of a student");
        StudentDto savedStudentDetails = studentService.deleteStudent(studentId);
        log.info("saved student details");
        return savedStudentDetails;
    }

}
