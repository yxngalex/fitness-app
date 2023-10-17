package org.alx.fitnessapp.util;

import org.alx.fitnessapp.exception.EmailValidationException;

import java.util.regex.Pattern;

public class EmailValidation {

    private static final String VALIDATOR = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static Boolean isValid(String email) throws EmailValidationException {
        if (email == null || email.isEmpty()) {
            throw new EmailValidationException("Invalid Email");
        }
        Pattern pattern = Pattern.compile(VALIDATOR);

        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new EmailValidationException("Invalid Email");
        }
    }
}
