package co.simplon.soninkrala.controllers.errors;

public class QuizErrorMessage extends RuntimeException {
    public QuizErrorMessage(String message) {
        super(message);
    }
}
