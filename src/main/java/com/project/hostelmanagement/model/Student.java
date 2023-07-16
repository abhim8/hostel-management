package com.project.hostelmanagement.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID studentId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "occupancy")
    private String occupancy;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin")
    private String pin;

    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "emergency_mobile", nullable = false)
    private String emergencyMobile;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;

    @Column(name = "organization_address", nullable = false)
    private String organizationAddress;

    @Column(name = "organization_city", nullable = false)
    private String organizationCity;

    @Column(name = "organization_pin", nullable = false)
    private String organizationPin;

    @Column(name = "id_card_type", nullable = false)
    private String idCardType;

    @Column(name = "id_card_number", nullable = false)
    private String idCardNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student otherStudent = (Student) o;
        return Objects.equals(firstName, otherStudent.firstName) &&
                Objects.equals(lastName, otherStudent.lastName) &&
                Objects.equals(mobile, otherStudent.mobile) &&
                Objects.equals(emergencyMobile, otherStudent.emergencyMobile) &&
                Objects.equals(organizationName, otherStudent.organizationName) &&
                Objects.equals(fatherName, otherStudent.fatherName) &&
                Objects.equals(gender, otherStudent.gender);
    }

}
