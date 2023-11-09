package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class DailyActivityExceptionAbstract extends AbstractFitnessException {
    public DailyActivityExceptionAbstract(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
