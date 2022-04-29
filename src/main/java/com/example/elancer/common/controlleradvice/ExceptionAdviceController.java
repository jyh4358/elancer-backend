package com.example.elancer.common.controlleradvice;

import com.example.elancer.common.exception.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.elancer")
public class ExceptionAdviceController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> Exception(Exception e) {
        log.error(e.getClass() + ": " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(Arrays.toString(e.getStackTrace())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ExceptionMessage> nullPointerException(NullPointerException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(Arrays.toString(e.getStackTrace())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
//        return ResponseEntity.error(e.getBindingResult().getFieldError().getDefaultMessage());
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(ExpectedException.class)
//    public ResponseEntity<Void> expectedException(ExpectedException e) {
//        return ResponseEntity.error(e.getMessage());
//    }
}
