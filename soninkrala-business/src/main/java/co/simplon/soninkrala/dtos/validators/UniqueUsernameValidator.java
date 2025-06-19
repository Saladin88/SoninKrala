package co.simplon.soninkrala.dtos.validators;

import co.simplon.soninkrala.serviceimplements.AccountServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final AccountServiceImpl accountService;

    public UniqueUsernameValidator(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username == null || username.trim().isEmpty()) {
            return true ;
        }
        return !accountService.existsByUsername(username);
    }
}
