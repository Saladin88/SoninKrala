package co.simplon.soninkrala.services;

import co.simplon.soninkrala.dtos.AccountCreationRequestBody;
import co.simplon.soninkrala.dtos.AccountCreationResponse;
import co.simplon.soninkrala.dtos.AccountLogInRequestBody;
import co.simplon.soninkrala.dtos.AccountLogInResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AccountService {

    AccountCreationResponse createAccount(AccountCreationRequestBody inputs);

    AccountLogInResponse login(AccountLogInRequestBody inputs);


    void validateAccount(String token, HttpServletResponse response) throws IOException;
}
