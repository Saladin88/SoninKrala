package co.simplon.soninkrala.dtos;

import co.simplon.soninkrala.dtos.validators.UniqueEmail;
import co.simplon.soninkrala.dtos.validators.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreationRequestBody(
        @NotBlank @Size(min = 1, max = 50) String firstname,
        @NotBlank @Size(min = 1, max = 80) String lastname,
        @NotBlank @Size(min = 1, max = 20) @UniqueUsername String username,
        @NotBlank  @Size(min = 10, max = 100) @UniqueEmail @Email String email,//Faire une regex pour bien controller input utilisateur
        @NotBlank @Size(min = 8, max = 20) String password // Faire egalement une regex
    ) {

    @Override
    public String toString() {
        return "AccountCreationDto{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]" + '\'' +
                '}';
    }
}

