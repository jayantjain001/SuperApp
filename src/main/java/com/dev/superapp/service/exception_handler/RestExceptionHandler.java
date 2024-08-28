package com.dev.superapp.service.exception_handler;

import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/***
 * To handles all exceptions gracefully always use RestControllerAdvice , so that no shit pops up on your UI :)
 */
@RestControllerAdvice     // ControllerAdvice +  @ResponseBody
public class RestExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e) {
        return new ResponseEntity<>("Request is invalid due to reason  : " + e.getMessage(),
                HttpStatus.NOT_FOUND);

    }
}
