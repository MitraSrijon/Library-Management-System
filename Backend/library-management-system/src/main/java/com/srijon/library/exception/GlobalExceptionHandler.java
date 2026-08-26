package com.srijon.library.exception;

import lombok.Data;
import org.springframework.boot.web.error.Error;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //If any field is missing
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){

        Map<String , String> errors = new HashMap<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(

                LocalDateTime.now(),
                400,
                "Validation Error",
                errors

        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    //If we could not find the book
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    //If we could not find book to delete
    @ExceptionHandler(BookDeletionException.class)
    public ResponseEntity<ErrorResponse> handleBookDeletionException(BookDeletionException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    //If more than 1 email exist
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Email already exists",
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    //If the book requested is not available
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleBookNotAvailableException(BookNotAvailableException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    //If loan is not found
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFoundException(LoanNotFoundException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    //If book is already returned
    @ExceptionHandler(LoanAlreadyReturnedException.class)
    public ResponseEntity<ErrorResponse> handleLoanAlreadyReturnedException(LoanAlreadyReturnedException ex){

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

}
