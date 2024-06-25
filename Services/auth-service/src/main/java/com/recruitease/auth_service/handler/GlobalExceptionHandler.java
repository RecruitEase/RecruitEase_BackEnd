package com.recruitease.auth_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(CustomerNotFoundException.class)
//    public ResponseEntity<String> handle(CustomerNotFoundException exp){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(exp.getMsg());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp){

        var errors=new HashMap<String,String >();
        exp.getBindingResult().getAllErrors()
                .forEach(error->{
                    var fieldName=((FieldError) error).getField();
                    var errorMsg=error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    //for authentication exceptions
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handle(AuthenticationException exp){

//        var errors=new HashMap<String,String >();
//        exp.getMessage().getAllErrors()
//                .forEach(error->{
//                    var fieldName=((FieldError) error).getField();
//                    var errorMsg=error.getDefaultMessage();
//                    errors.put(fieldName,errorMsg);
//                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }


    //for sql exceptions
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handle(SQLException exp){

//        var errors=new HashMap<String,String >();
//        exp.getMessage().getAllErrors()
//                .forEach(error->{
//                    var fieldName=((FieldError) error).getField();
//                    var errorMsg=error.getDefaultMessage();
//                    errors.put(fieldName,errorMsg);
//                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }
}
