package com.project.hostelmanagement.service;

public interface AuthService {
    boolean authenticate(String mail, String password);

    boolean saveCredentials(String mail, String password);
}
