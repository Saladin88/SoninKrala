package co.simplon.soninkrala.controllers.errors;
public class ReferentialDataError extends RuntimeException{
    public ReferentialDataError(String message) {
        super(message);
    }
}
