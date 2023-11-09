package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class WorkoutException extends AbstractFitnessException {

    public WorkoutException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
