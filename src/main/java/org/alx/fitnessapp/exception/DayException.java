package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class DayException extends AbstractFitnessException {

    public DayException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
