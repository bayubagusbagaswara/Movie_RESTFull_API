package com.bayu.movie.controller;

import com.bayu.movie.dto.MessageResponse;
import com.bayu.movie.exception.AppException;
import com.bayu.movie.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> validatorHandler(ConstraintViolationException constraintViolationException) {
        MessageResponse messageResponse = MessageResponse.builder()
                .success(Boolean.FALSE)
                .message(constraintViolationException.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
        MessageResponse messageResponse = MessageResponse.builder()
                .success(Boolean.FALSE)
                .message(resourceNotFoundException.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageResponse> appException(AppException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
