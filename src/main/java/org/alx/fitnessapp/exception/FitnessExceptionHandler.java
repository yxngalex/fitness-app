package org.alx.fitnessapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FitnessExceptionHandler {

    @ExceptionHandler(AbstractFitnessException.class)
    public ResponseEntity<String> handleFitnessException(AbstractFitnessException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException() {
//        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
