package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AbstractFitnessException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
