package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.AnswerChoice;
import co.simplon.soninkrala.dtos.QuestionQuiz;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.entities.AnswerEntity;
import co.simplon.soninkrala.entities.QuestionEntity;

import java.util.List;

import static co.simplon.soninkrala.utils.DateHoursUtil.getDateToLocalDateTimeIfExist;

public class QuizMapper {

    public static  List<QuestionQuiz> fromEntitiesToDtoList(List<QuestionEntity> questionEntities) {
        return questionEntities.stream().map(q -> new QuestionQuiz(q.getId(),q.getQuestion(),getDateToLocalDateTimeIfExist(q.getCreationDate()))).toList();

    }
    public static AnswerChoice toDtoAnswer(AnswerEntity answerEntity) {
        return new AnswerChoice(answerEntity.getAnswer(), getDateToLocalDateTimeIfExist(answerEntity.getCreationDate()));
    }

    public static List<UserAnswerDto> toAnswerEntities(List<AnswerEntity> answers) {
        return answers.stream().map(QuizMapper :: toAnswerDto).toList();
    }

    public static UserAnswerDto toAnswerDto(AnswerEntity answerEntity) {
        UserAnswerDto userAnswerDto = new UserAnswerDto(answerEntity.getIdQuestion().getId(), answerEntity.getAnswer());
        return userAnswerDto;
    }
}
