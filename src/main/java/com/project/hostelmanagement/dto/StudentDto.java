package com.project.hostelmanagement.dto;

import com.project.hostelmanagement.model.Student;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String studentId;
    private String firstName;
    private String lastName;
    private GENDER gender;
    private String occupancy;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String mobile;
    private String emergencyMobile;
    private String email;
    private String fatherName;
    private String organizationName;
    private String organizationAddress;
    private String organizationCity;
    private String organizationPin;
    private IDTYPES idCardType;
    private String idCardNumber;

    public StudentDto(Student student){
        this.setStudentId(student.getStudentId().toString());
        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.setGender(GENDER.get(student.getGender()));
        this.setOccupancy(student.getOccupancy());
        this.setAddress(student.getAddress());
        this.setCity(student.getCity());
        this.setState(student.getState());
        this.setPin(student.getPin());
        this.setMobile(student.getMobile());
        this.setEmergencyMobile(student.getEmergencyMobile());
        this.setEmail(student.getEmail());
        this.setFatherName(student.getFatherName());
        this.setOrganizationName(student.getOrganizationName());
        this.setOrganizationAddress(student.getOrganizationAddress());
        this.setOrganizationCity(student.getOrganizationCity());
        this.setOrganizationPin(student.getOrganizationPin());
        this.setIdCardType(IDTYPES.get(student.getIdCardType()));
        this.setIdCardNumber(student.getIdCardNumber());
    }

}
