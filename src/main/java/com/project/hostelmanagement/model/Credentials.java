package com.project.hostelmanagement.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder
@Table(name = "credentials")
public class Credentials {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "email", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

}
