package com.project.hostelmanagement.controller;

import com.project.hostelmanagement.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/v1/authenticate")
    private boolean authenticate(
            @RequestParam(value = "email", defaultValue = "") String mail,
            @RequestParam(value = "password", defaultValue = "") String password
    ){
        log.info("authenticating");
        boolean isValid = authService.authenticate(mail, password);
        log.info("authenticated by mail{}, password{}", mail, password);
        log.info("authentication is {}", isValid);
        return isValid;
    }

    @PostMapping("/v1/signup")
    private boolean saveCredentials(
            @RequestParam(value = "email", defaultValue = "") String mail,
            @RequestParam(value = "password", defaultValue = "") String password
    ){
        log.info("saving credentials of user");
        boolean savedCredentials = authService.saveCredentials(mail, password);
        log.info("user credentials are saved if true: {}", savedCredentials);
        return savedCredentials;
    }
}
