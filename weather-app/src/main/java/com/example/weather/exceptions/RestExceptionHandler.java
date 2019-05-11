package com.example.weather.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

    @RestControllerAdvice
    public class RestExceptionHandler extends ResponseEntityExceptionHandler {

        @Override
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, "Validation Error", ex.getBindingResult().toString());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(InvalidMediaTypeException.class)
        private ResponseEntity<ErrorModel> handleEntityNotFound(InvalidMediaTypeException ex){
            ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "invalid Media Type", ex.getMessage());

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(HttpClientErrorException.class)
        private ResponseEntity<ErrorModel> handleNotFoundEntityNotFound(HttpClientErrorException ex){
            ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, "invalid Country Not present", ex.getMessage());

            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

