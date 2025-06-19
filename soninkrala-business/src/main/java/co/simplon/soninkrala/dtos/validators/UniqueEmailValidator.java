package co.simplon.soninkrala.dtos.validators;

import co.simplon.soninkrala.serviceimplements.AccountServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final AccountServiceImpl accountService;

    public UniqueEmailValidator(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email == null || email.isEmpty()) {
            return true;
        }
        return ! accountService.existsByEmail(email);
    }
}
