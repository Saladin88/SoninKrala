package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.components.EmailSender;
import co.simplon.soninkrala.config.JwtProvider;
import co.simplon.soninkrala.controllers.errors.BadCredentialErrorMessage;
import co.simplon.soninkrala.dtos.AccountCreationRequestBody;
import co.simplon.soninkrala.dtos.AccountCreationResponse;
import co.simplon.soninkrala.dtos.AccountLogInRequestBody;
import co.simplon.soninkrala.dtos.AccountLogInResponse;
import co.simplon.soninkrala.entities.AccountEntity;
import co.simplon.soninkrala.entities.RoleEntity;
import co.simplon.soninkrala.jpaRepositories.AccountJpaRepo;
import co.simplon.soninkrala.jpaRepositories.RoleJpaRepo;
import co.simplon.soninkrala.mappers.AccountMapper;
import co.simplon.soninkrala.services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class AccountServiceImpl implements AccountService {

    private final JwtProvider jwtProvider;
    private final AccountJpaRepo accountJpaRepo;
    private final RoleJpaRepo roleJpaRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Value("${co.simplon.soninkrala.token.expiration-minutes}")
    private int tokenExpMinutes;

    @Value("${co.simplon.soninkrala.email.verification-url-back}")
    private String verificationUrlBack;

    @Value("${co.simplon.soninkrala.email.redirection-url-front}")
    private String redirectionUrlFront;

    public AccountServiceImpl(AccountJpaRepo accountJpaRepo, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, RoleJpaRepo roleJpaRepo, EmailSender emailSender) {
        this.accountJpaRepo = accountJpaRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.roleJpaRepo = roleJpaRepo;
        this.emailSender = emailSender;
    }

    @Transactional
    @Override
    public AccountCreationResponse createAccount(AccountCreationRequestBody inputs) {
        AccountEntity account = AccountMapper.toAccountEntity(inputs);
        RoleEntity role = roleJpaRepo.findByName("MEMBER").orElseThrow(() -> new RuntimeException("Default MEMBER role not found"));
        account.setRole(role);
        accountJpaRepo.save(account);
        UUID tokenUUID = UUID.randomUUID();
        account.setUuidToken(tokenUUID);
        account.setUuidTokenExpiration(LocalDateTime.now().plusMinutes(tokenExpMinutes));
        sendValidationEmail(inputs.email(), inputs.username(), tokenUUID.toString());
        return AccountMapper.toAccountCreationResponse(account);
    }

    @Transactional
    @Override
    public void validateAccount(String token, HttpServletResponse response) throws IOException {
        UUID uuid = UUID.fromString(token);
        Optional<AccountEntity> optionalAccount = accountJpaRepo.findByUuidToken(uuid);
        response.setHeader("Cache-Control", "no-store"); //empecher l'historique cache
        if(!optionalAccount.isPresent()) {
            response.sendRedirect(redirectionUrlFront+"?validation=error");
            return;
        }
        if(optionalAccount.get().getUuidTokenExpiration().isBefore(LocalDateTime.now())) {
            response.sendRedirect(redirectionUrlFront+"?validation=expired");
        }
        optionalAccount.get().setVerify(true);
        optionalAccount.get().setUuidToken(null);
        optionalAccount.get().setUuidTokenExpiration(null);
        accountJpaRepo.save(optionalAccount.get());
        response.sendRedirect(redirectionUrlFront+"?validation=valid");
    }

    private void sendValidationEmail(String email, String username, String token) {
        String link = verificationUrlBack + "?token=" + token;
        String body = loadConfirmationMailTemplate(username, link);
        try{
            emailSender.sendMail(email,"<h1>[NO-REPLY] Validation account</h1>", body);
        }catch (MessagingException e){
            throw new RuntimeException("Failed to send validation email", e);
        }
    }
    private String loadConfirmationMailTemplate(String username, String link) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/email-account-validation-template.html");
            String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return template
                    .replace("${link}", link)
                    .replace("${pseudo}", username);
        }catch (Exception e) {
            throw new RuntimeException("Failed to load email template", e);

        }
    }

    @Override
    public AccountLogInResponse login(AccountLogInRequestBody inputs) {
        AccountEntity accountEntity = accountJpaRepo.findByUsernameIgnoreCase(inputs.username())
                                                .orElseThrow(()-> new BadCredentialErrorMessage("No existing account with : " + inputs.username()));

        boolean isPasswordMatch = passwordEncoder.matches(inputs.password(), accountEntity.getPassword());
        if (!isPasswordMatch) {
            throw new BadCredentialErrorMessage("Bad credentials for user : " + inputs.username());
        }
        String jwt = jwtProvider.generateToken(inputs.username());
        return AccountMapper.toAccountLogInResponse(jwt, accountEntity);
    }

    @Scheduled(fixedRate = 20 * 60 * 1000) //20 minutes interval regulier
    @Transactional
    void deleteUnverifiedAccount() {
        LocalDateTime nowMinus15min = LocalDateTime.now().minusMinutes(5);
        accountJpaRepo.deleteAllByVerifyFalseAndCreationDateBefore(nowMinus15min);
        System.out.println("Suppression des comptes non vérifiés avant : " + nowMinus15min);
    }


    public boolean existsByUsername(String username) {
       return accountJpaRepo.existsByUsernameIgnoreCase(username);
    }

    public boolean existsByEmail(String email) {
        return accountJpaRepo.existsByEmailIgnoreCase(email);
    }

}
