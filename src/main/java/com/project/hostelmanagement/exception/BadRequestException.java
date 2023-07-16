package com.project.hostelmanagement.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {

    private final String message;
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    public BadRequestException(String message) {
        this.message = message;
    }
}
