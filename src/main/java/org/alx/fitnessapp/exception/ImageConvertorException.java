package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public class ImageConvertorException extends AbstractFitnessException {
    public ImageConvertorException(String msg) {
        super(msg);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_GATEWAY;
    }
}
