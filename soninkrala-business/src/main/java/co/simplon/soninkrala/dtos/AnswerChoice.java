package co.simplon.soninkrala.dtos;

import co.simplon.soninkrala.entities.AnswerEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

public record AnswerChoice(
        @NotBlank
        @Size(min=1,max = 50)
        String answer,
        LocalDateTime creation_date

) {

        public static List<AnswerChoice> fromEntitiesToDtoAnswerList(List<AnswerEntity> answerEntities) {
                return answerEntities.stream().map(AnswerChoice::toDtoAnswer).toList();
        }

        public static AnswerChoice toDtoAnswer(AnswerEntity answerEntity) {
                return new AnswerChoice(answerEntity.getAnswer(), getDateToLocalDateTimeIfExist(answerEntity.getCreationDate()));
        }

        private static LocalDateTime getDateToLocalDateTimeIfExist(OffsetDateTime offsetDateTime) {
                return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime() : null;
        }
}
