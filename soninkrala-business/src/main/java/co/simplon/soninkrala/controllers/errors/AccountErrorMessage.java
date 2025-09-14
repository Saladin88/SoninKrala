package co.simplon.soninkrala.controllers.errors;
public class AccountErrorMessage extends RuntimeException{
    public AccountErrorMessage(String message) {
        super(message);
    }
}
