package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class DayExceptionAbstract extends AbstractFitnessException {

    public DayExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
