package com.tomctrlcoding.library.validation;

import com.tomctrlcoding.library.validation.constraints.ISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> {
    @Override
    public void initialize(ISBN constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return org.apache.commons.validator.routines.ISBNValidator.getInstance().isValid(s);
    }
}
