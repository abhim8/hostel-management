package com.project.hostelmanagement.repository;

import com.project.hostelmanagement.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    Optional<Credentials> findByMail(String mail);
}
