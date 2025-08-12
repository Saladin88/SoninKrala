package co.simplon.soninkrala.controllers.errors;

public class EmailNotVerifiedException extends  RuntimeException {
    public EmailNotVerifiedException(String message) {
        super(message);
    }
}
