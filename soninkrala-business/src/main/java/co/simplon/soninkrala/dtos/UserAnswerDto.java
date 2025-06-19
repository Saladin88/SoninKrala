package co.simplon.soninkrala.dtos;

import co.simplon.soninkrala.dtos.validators.ExistingQuestionId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserAnswerDto(
        @NotNull
        @ExistingQuestionId
        Integer questionId,
        @NotBlank
        String answer //dans le futur changer en liste dans le cas de multiple possible reponse
) {
}
