package co.simplon.soninkrala.dtos;

public record AccountLogInResponse(
        String token,
        String username,
        String role
) {
}
