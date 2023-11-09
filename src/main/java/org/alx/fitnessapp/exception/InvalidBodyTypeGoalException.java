package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class InvalidBodyTypeGoalException extends AbstractFitnessException {
    public InvalidBodyTypeGoalException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
