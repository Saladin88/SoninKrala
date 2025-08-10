package co.simplon.soninkrala.services;

import co.simplon.soninkrala.dtos.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AccountService {

    AccountCreationResponse createAccount(AccountCreationRequestBody inputs);

    AccountLogInResponse login(AccountLogInRequestBody inputs);

    void validateAccount(String token, HttpServletResponse response) throws IOException;

    MemberGeneralInfo getMemberGeneralInfo(String username);
}
