package co.simplon.soninkrala.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountLogInRequestBody(
        @NotBlank @Size(min = 1, max = 20)  String username,
        @NotBlank @Size(min = 8, max = 20)  String password) {
}
