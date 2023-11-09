package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class MealCreationExceptionAbstract extends AbstractFitnessException {

    public MealCreationExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
