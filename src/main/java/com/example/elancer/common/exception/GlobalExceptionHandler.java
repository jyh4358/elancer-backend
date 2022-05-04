package com.example.elancer.common.exception;

import com.example.elancer.enterprise.exception.CheckPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(CheckPasswordException.class)
//    public ResponseEntity<ErrorResponse> handleCheckPasswordException(CheckPasswordException ex) {
//        log.error("checkPasswordException", ex);
//        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
//    }
}
