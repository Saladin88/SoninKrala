package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.AccountCreationRequestBody;
import co.simplon.soninkrala.dtos.AccountCreationResponse;
import co.simplon.soninkrala.dtos.AccountLogInResponse;
import co.simplon.soninkrala.entities.AccountEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountMapper {

    private AccountMapper() {
        //Static methods
    }
    static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static AccountEntity toAccountEntity(AccountCreationRequestBody inputs) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(inputs.username());
        accountEntity.setFirstname(inputs.firstname());
        accountEntity.setLastname(inputs.lastname());
        accountEntity.setEmail(inputs.email());
        accountEntity.setPassword(passwordEncoder.encode(inputs.password()));
        accountEntity.setVerify(false);
        return accountEntity;
    }

    public static AccountLogInResponse toAccountLogInResponse(String token, AccountEntity account) {
       return new AccountLogInResponse(token,account.getUsername(), account.getRole().getName());
    }

    public static AccountCreationResponse toAccountCreationResponse(AccountEntity account) {
        return new AccountCreationResponse(account.getUsername(), account.getFirstname(), account.getLastname(), account.getEmail());
    }
}
