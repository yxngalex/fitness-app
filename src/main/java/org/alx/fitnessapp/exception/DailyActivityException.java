package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class DailyActivityException extends AbstractFitnessException {
    public DailyActivityException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
