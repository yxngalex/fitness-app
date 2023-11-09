package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailValidationExceptionAbstract extends AbstractFitnessException {

    public InvalidEmailValidationExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
