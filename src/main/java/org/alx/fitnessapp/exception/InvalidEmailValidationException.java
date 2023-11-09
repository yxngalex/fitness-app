package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailValidationException extends AbstractFitnessException {

    public InvalidEmailValidationException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
