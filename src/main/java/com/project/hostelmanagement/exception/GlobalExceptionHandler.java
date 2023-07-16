package com.project.hostelmanagement.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(errors);
        return buildResponseEntity(errorResponse);
    }


    @ExceptionHandler(InvalidFormatException.class)
    protected @NotNull ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected @NotNull ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE);
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setViolations(List.of(new Violation(ex.getName(), "Invalid Input")));
        errorResponse.setMessage(validationErrorResponse);
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleServiceInvocationException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus());
        errorResponse.setMessage(ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(ValidationException .class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage(ex.getCause().getMessage());
        return buildResponseEntity(errorResponse);
    }


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE);
        errorResponse.setMessage(getViolationMessage(ex));
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND);
        errorResponse.setMessage(getMessageFromHttpMessageNotReadableException(ex));
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleBindException(ex, headers, status, request);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE);
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setViolations(List.of(new Violation(ex.getParameterName(), ex.getMessage())));
        errorResponse.setMessage(validationErrorResponse);
        return buildResponseEntity(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE);
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        validationErrorResponse.setViolations(List.of(new Violation(ex.getPropertyName(), "Required type "+ex.getRequiredType() + " , provided "+ ex.getValue())));
        errorResponse.setMessage(validationErrorResponse);
        return buildResponseEntity(errorResponse);
    }

    private String getMessageFromHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if(ex.getCause() instanceof InvalidFormatException) {
            // Get response
            StringBuilder message = new StringBuilder();
            List<JsonMappingException.Reference> references = ((InvalidFormatException)ex.getCause()).getPath();
            for (JsonMappingException.Reference reference : references) {
                if (reference.getFieldName() != null) {
                    message.append(reference.getFieldName()).append(",");
                }
            }
            return String.valueOf(message.append(" are incorrect"));
        } else {
            if(Objects.requireNonNullElse(ex.getMessage(), "").contains("Required request body is missing")){
                return "Required request body is missing";
            }
            return StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : "Invalid Input";
        }
    }

    private Object getViolationMessage(BindException exception) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            String jsonAliasFieldName = FieldErrorUtil.getJsonAliasValue(fieldError, exception.getBindingResult())[0];
            jsonAliasFieldName = StringUtils.isNotEmpty(jsonAliasFieldName) ? jsonAliasFieldName : fieldError.getField();
            validationErrorResponse.getViolations().add(new Violation(jsonAliasFieldName, fieldError.getDefaultMessage()));
        }
        return validationErrorResponse;
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Unexpected Exception: "+ ex);
        errorResponse.setMessage(ex.getMessage());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
