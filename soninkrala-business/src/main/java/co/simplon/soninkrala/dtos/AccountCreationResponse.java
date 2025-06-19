package co.simplon.soninkrala.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountCreationResponse(
        String firstname, String lastname,
        String username,
        String email
        ) {
}
