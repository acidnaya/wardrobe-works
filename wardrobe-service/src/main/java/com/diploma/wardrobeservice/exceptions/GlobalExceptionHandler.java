package com.diploma.wardrobeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFound(FileNotFoundException ex) {
//        return ResponseEntity.notFound().build();
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
//        return ResponseEntity.notFound().build();
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotAccessibleException.class)
    public ResponseEntity<String> handleResourceNotAccessible(ResourceNotAccessibleException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleResourceNotFound(NoSuchElementException ex) {
//        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(RequestNotProcessedException.class)
    public ResponseEntity<String> handleCantBeProcessed(RequestNotProcessedException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

//package com.diploma.wardrobeservice.exceptions;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.http.ProblemDetail;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleAll(Throwable ex) {
//        ex.printStackTrace(); // логим всё
//        return ProblemDetail.forStatus(500);
//    }
//}