package co.simplon.soninkrala.dtos;

import co.simplon.soninkrala.entities.AnswerEntity;
import co.simplon.soninkrala.entities.QuestionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

public record QuestionQuiz(
        int id,
        String question,
        LocalDateTime creation_date,
        String photoUrl,
        String photoName) {

//
//        private static QuestionQuiz toQuestionDto(QuestionEntity questionEntity, List<AnswerEntity> answerEntities) {
//                return new QuestionQuiz(questionEntity.getId(),questionEntity.getQuestion(), getDateToLocalDateTimeIfExist(questionEntity.getCreationDate()));
//        }

        private static LocalDateTime getDateToLocalDateTimeIfExist(OffsetDateTime offsetDateTime) {
                return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime() : null;
        }
}
