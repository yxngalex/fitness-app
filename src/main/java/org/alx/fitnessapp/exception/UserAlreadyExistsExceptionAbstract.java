package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsExceptionAbstract extends AbstractFitnessException {

    public UserAlreadyExistsExceptionAbstract(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
