package org.alx.fitnessapp.util;

import org.alx.fitnessapp.exception.AgeValidationException;
import org.alx.fitnessapp.exception.EmailValidationException;

import java.util.regex.Pattern;

public class Validator {

    private static final String VALIDATOR = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static Boolean isEmailValid(String email) throws EmailValidationException {
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

    public static Boolean isAgeValid(int age) throws AgeValidationException{
        if (age >= 18 && age <= 99) {
            return true;
        } else {
            throw new AgeValidationException("You need to be at least 18 years old!");
        }
    }
}
