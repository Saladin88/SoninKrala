package co.simplon.soninkrala.dtos.validators;

import co.simplon.soninkrala.serviceimplements.AccountServiceImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AudioFormatValidator implements ConstraintValidator<AudioFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}