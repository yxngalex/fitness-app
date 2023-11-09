package org.alx.fitnessapp.validation;

import org.alx.fitnessapp.exception.InvalidAgeValidationExceptionAbstract;
import org.alx.fitnessapp.exception.InvalidEmailValidationExceptionAbstract;

import java.util.regex.Pattern;

public class Validator {

    private static final String VALIDATOR = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static Boolean isEmailValid(String email) throws InvalidEmailValidationExceptionAbstract {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailValidationExceptionAbstract("Invalid Email");
        }
        Pattern pattern = Pattern.compile(VALIDATOR);

        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            throw new InvalidEmailValidationExceptionAbstract("Invalid Email");
        }
    }

    public static Boolean isAgeValid(int age) throws InvalidAgeValidationExceptionAbstract {
        if (age >= 18 && age <= 99) {
            return true;
        } else {
            throw new InvalidAgeValidationExceptionAbstract("You need to be at least 18 years old!");
        }
    }
}
