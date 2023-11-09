package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidAgeValidationExceptionAbstract extends AbstractFitnessException {
    public InvalidAgeValidationExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
