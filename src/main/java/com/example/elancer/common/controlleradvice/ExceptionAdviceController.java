package com.example.elancer.common.controlleradvice;

import com.example.elancer.common.exception.ExceptionMessage;
import com.example.elancer.common.exception.WrongRequestException;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.interviewproject.exception.PresentInterviewFreelancerException;
import com.example.elancer.waitproject.exception.CheckInterviewStatusException;
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
    public ResponseEntity<ExceptionMessage> handleException(Exception e) {
        log.error(e.getClass() + ": " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(Arrays.toString(e.getStackTrace())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotExistFreelancerException.class})
    public ResponseEntity<ExceptionMessage> handleNotExistFreelancerException(NotExistFreelancerException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotExistFreelancerProfileException.class})
    public ResponseEntity<ExceptionMessage> handleNotExistFreelancerProfileException(NotExistFreelancerProfileException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({WrongRequestException.class})
    public ResponseEntity<ExceptionMessage> handleWrongRequestException(WrongRequestException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotExistEnterpriseException.class})
    public ResponseEntity<ExceptionMessage> handleNotExistEnterpriseException(NotExistEnterpriseException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PresentInterviewFreelancerException.class})
    public ResponseEntity<ExceptionMessage> handlePresentInterviewFreelancerException(PresentInterviewFreelancerException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CheckInterviewStatusException.class})
    public ResponseEntity<ExceptionMessage> handleCheckInterviewStatusException(CheckInterviewStatusException e) {
        log.error(e.getClass() + ": " + e.getMessage());
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }




//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Void> methodArgumentNotValidException(MethodArgumentNotValidException e) {
//        return ResponseEntity.error(e.getBindingResult().getFieldError().getDefaultMessage());
//    }

}
