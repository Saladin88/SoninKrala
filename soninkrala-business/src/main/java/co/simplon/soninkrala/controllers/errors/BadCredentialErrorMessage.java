package co.simplon.soninkrala.controllers.errors;

import org.springframework.security.authentication.BadCredentialsException;

public class BadCredentialErrorMessage extends BadCredentialsException {

    public BadCredentialErrorMessage(String msg) {
        super(msg);
    }
}
