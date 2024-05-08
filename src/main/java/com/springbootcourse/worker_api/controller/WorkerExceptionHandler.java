package com.springbootcourse.worker_api.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.springbootcourse.worker_api.error.ErrorResponse;
import com.springbootcourse.worker_api.error.WorkerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class WorkerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), new Timestamp(System.currentTimeMillis()).toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exc) {
        StringBuilder strBuilder = new StringBuilder();

        exc.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(fieldName).append(" : ").append(message).append(" \n ");
        });

        return new ResponseEntity<>( new ErrorResponse(HttpStatus.BAD_REQUEST.value(), strBuilder.toString(),new Timestamp(System.currentTimeMillis()).toString()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(WorkerNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), new Timestamp(System.currentTimeMillis()).toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(InvalidFormatException ex) {
        String message = "Failed to parse date: " + ex.getValue();
        return new ResponseEntity<>( new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message,new Timestamp(System.currentTimeMillis()).toString()), HttpStatus.BAD_REQUEST);
    }

}
