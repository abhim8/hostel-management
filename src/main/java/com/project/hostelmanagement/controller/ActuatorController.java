package com.project.hostelmanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(produces = "application/json")
public class ActuatorController {
    @GetMapping("/actuator/health")
    public String sampleEndPoint(){
        log.info("reached hostel-management service");
        return "Up";
    }
}
