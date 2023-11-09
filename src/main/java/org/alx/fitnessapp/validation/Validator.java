package org.alx.fitnessapp.validation;

import org.alx.fitnessapp.exception.InvalidAgeValidationException;
import org.alx.fitnessapp.exception.InvalidEmailValidationException;

import java.util.regex.Pattern;

public class Validator {

    private static final String VALIDATOR = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static Boolean isEmailValid(String email) throws InvalidEmailValidationException {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailValidationException("Invalid Email");
        }
        Pattern pattern = Pattern.compile(VALIDATOR);

        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new InvalidEmailValidationException("Invalid Email");
        }
    }

    public static Boolean isAgeValid(int age) throws InvalidAgeValidationException {
        if (age >= 18 && age <= 99) {
            return true;
        } else {
            throw new InvalidAgeValidationException("You need to be at least 18 years old!");
        }
    }
}
