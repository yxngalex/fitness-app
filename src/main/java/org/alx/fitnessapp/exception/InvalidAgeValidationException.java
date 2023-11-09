package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidAgeValidationException extends AbstractFitnessException {
    public InvalidAgeValidationException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
