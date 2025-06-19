package co.simplon.soninkrala.controllers.errors;

public class FetchAllDataQuizErrorMessage extends RuntimeException {
    public FetchAllDataQuizErrorMessage(String message, Exception e) {
        super(message, e);
    }
}
