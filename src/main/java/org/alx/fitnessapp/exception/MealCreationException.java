package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class MealCreationException extends AbstractFitnessException {

    public MealCreationException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
