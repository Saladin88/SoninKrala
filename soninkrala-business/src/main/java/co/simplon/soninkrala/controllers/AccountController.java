package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.components.EmailSender;
import co.simplon.soninkrala.dtos.*;
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
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
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
    @GetMapping("/member-profile-info")
    public MemberGeneralInfo getMemberGeneralInfo(Principal principal) {
        return accountService.getMemberGeneralInfo(principal.getName());
    }

}
