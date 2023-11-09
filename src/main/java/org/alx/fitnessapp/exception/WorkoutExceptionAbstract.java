package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class WorkoutExceptionAbstract extends AbstractFitnessException {

    public WorkoutExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
