package org.alx.fitnessapp.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractFitnessException extends RuntimeException {
    public AbstractFitnessException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
}
