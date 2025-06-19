package co.simplon.soninkrala.controllers.errors;

public class AccountAlreadyExistsErrorMessage extends RuntimeException {
    public AccountAlreadyExistsErrorMessage(String message) {
        super(message);
    }
}
