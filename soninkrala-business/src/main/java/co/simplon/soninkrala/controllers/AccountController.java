package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.components.EmailSender;
import co.simplon.soninkrala.dtos.AccountCreationRequestBody;
import co.simplon.soninkrala.dtos.AccountCreationResponse;
import co.simplon.soninkrala.dtos.AccountLogInRequestBody;
import co.simplon.soninkrala.dtos.AccountLogInResponse;
import co.simplon.soninkrala.entities.AccountEntity;
import co.simplon.soninkrala.jpaRepositories.AccountJpaRepo;
import co.simplon.soninkrala.services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/soninkrala/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(code= HttpStatus.CREATED)
    AccountCreationResponse createAccount(@RequestBody @Valid AccountCreationRequestBody inputs) {
        return accountService.createAccount(inputs);
    }

    @PostMapping("/log-in")
    @ResponseStatus(code=HttpStatus.CREATED)
    AccountLogInResponse login(@RequestBody @Valid AccountLogInRequestBody inputs) {
        return accountService.login(inputs);
    }

    @GetMapping("/verify")
    public void validateAccount(@RequestParam String token, HttpServletResponse response) throws IOException {
        accountService.validateAccount(token, response);
    }

}
