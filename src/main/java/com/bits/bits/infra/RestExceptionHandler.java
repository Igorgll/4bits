package com.bits.bits.infra;

import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.exceptions.NoContentException;
import com.bits.bits.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> userNotFoundException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @ExceptionHandler(NoContentException.class)
    private ResponseEntity<String> noContentException(NoContentException exception) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(CannotAccessException.class)
    private ResponseEntity<String> cannotAccessException(CannotAccessException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot access this resource.");
    }
}
