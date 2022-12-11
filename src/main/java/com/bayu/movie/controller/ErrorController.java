package com.bayu.movie.controller;

import com.bayu.movie.dto.MessageResponse;
import com.bayu.movie.dto.WebResponse;
import com.bayu.movie.exception.AppException;
import com.bayu.movie.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new WebResponse<>(Boolean.FALSE, "Bad Request", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResponse> resourceNotFoundHandler(ResourceNotFoundException resourceNotFoundException) {
        MessageResponse messageResponse = MessageResponse.builder()
                .success(Boolean.FALSE)
                .message(resourceNotFoundException.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageResponse> appException(AppException exception) {
        MessageResponse messageResponse = MessageResponse.builder()
                .success(Boolean.FALSE)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
