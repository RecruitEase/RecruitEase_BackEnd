package com.recruitease.joblisting.handler;

import com.recruitease.joblisting.dto.ResponseDTO;
import com.recruitease.joblisting.util.CodeList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    public ResponseEntity<ResponseDTO> handle(MethodArgumentNotValidException exp){

        var errors=new HashMap<String,String >();
        exp.getBindingResult().getAllErrors()
                .forEach(error->{
                    var fieldName=((FieldError) error).getField();
                    var errorMsg=error.getDefaultMessage();
                    errors.put(fieldName,errorMsg);
                });

        var responseDto=new ResponseDTO();
        responseDto.setCode(CodeList.RSP_ERROR);
        responseDto.setMessage("Invalid Data");
        responseDto.setErrors(errors);


        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    //for authentication exceptions
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDTO> handle(AuthenticationException exp){

//        var errors=new HashMap<String,String >();
//        exp.getMessage().getAllErrors()
//                .forEach(error->{
//                    var fieldName=((FieldError) error).getField();
//                    var errorMsg=error.getDefaultMessage();
//                    errors.put(fieldName,errorMsg);
//                });
        var responseDto=new ResponseDTO();
        responseDto.setErrors("Incorrect email or password");
        return new ResponseEntity<>(responseDto,HttpStatus.UNAUTHORIZED);

    }


    //for sql exceptions
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDTO> handle(SQLException exp){

//        var errors=new HashMap<String,String >();
//        exp.getMessage().getAllErrors()
//                .forEach(error->{
//                    var fieldName=((FieldError) error).getField();
//                    var errorMsg=error.getDefaultMessage();
//                    errors.put(fieldName,errorMsg);
//                });

        var responseDto=new ResponseDTO();
        responseDto.setCode(CodeList.RSP_FAIL);
        responseDto.setMessage("Error occurred while writing to db");
        responseDto.setContent(exp.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO> handle(AccessDeniedException exp){

        var responseDto=new ResponseDTO();
        responseDto.setCode(CodeList.RSP_NOT_AUTHORISED);
        responseDto.setMessage("Unauthorized access!");
        responseDto.setContent(exp.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handle(IllegalArgumentException exp){
        var responseDto=new ResponseDTO();
        responseDto.setCode(CodeList.RSP_ERROR);
        responseDto.setMessage("Invalid Data");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
