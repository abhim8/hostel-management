package com.project.hostelmanagement.service;

import com.project.hostelmanagement.dto.StudentDto;
import com.project.hostelmanagement.exception.BadRequestException;
import com.project.hostelmanagement.model.Student;
import com.project.hostelmanagement.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StudentDto> fetchStudents() {
        List<Student> studentList = studentRepository.findAll();
        log.info("fetched studentList {}", studentList);
        log.info("converting to student-dto");
        List<StudentDto> studentDtoList = convertStudentToStudentDtoList(studentList);
        log.info("converted to student-dto");
        return studentDtoList;
    }

    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        Student studentDetailsToSave = convertStudentDtoToStudent(studentDto);
        boolean exists = studentRepository.findAll().stream()
                .anyMatch(student -> student.equals(studentDetailsToSave));
        if(exists){
            throw new BadRequestException("Student Details Already exists!");
        }
        Student student = studentRepository.save(studentDetailsToSave);
        log.info("saved student details");
        studentDto.setStudentId(student.getStudentId().toString());
        return studentDto;
    }

    @Override
    public StudentDto deleteStudent(String studentId) {
        Optional<Student> student = studentRepository.findById(UUID.fromString(studentId));
        if(student.isPresent()) studentRepository.deleteById(UUID.fromString(studentId));
        else throw new BadRequestException("User Doesnt exist");
        return convertStudentToStudentDto(student.get());
    }


    private StudentDto convertStudentToStudentDto(Student student) {
        return new StudentDto(student);
    }

    private List<StudentDto> convertStudentToStudentDtoList(List<Student> studentList) {
        return studentList.stream().map(
                StudentDto::new
        ).collect(Collectors.toList());
    }

    private Student convertStudentDtoToStudent(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
