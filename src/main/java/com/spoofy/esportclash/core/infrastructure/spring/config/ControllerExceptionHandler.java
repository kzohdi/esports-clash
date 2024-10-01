package com.spoofy.esportclash.core.infrastructure.spring.config;

import com.spoofy.esportclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportclash.core.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
