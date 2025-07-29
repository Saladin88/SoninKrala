package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.AnswerChoice;
import co.simplon.soninkrala.dtos.QuestionQuiz;
import co.simplon.soninkrala.dtos.QuizDto;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.entities.AnswerEntity;
import co.simplon.soninkrala.entities.QuestionEntity;
import co.simplon.soninkrala.entities.QuizEntity;

import java.util.List;

import static co.simplon.soninkrala.utils.DateHoursUtil.getDateToLocalDateTimeIfExist;

public class QuizMapper {

    public static  QuestionQuiz toQuestionQuiz(QuestionEntity questionEntity) {
        return new QuestionQuiz(questionEntity.getId(),questionEntity.getQuestion(),getDateToLocalDateTimeIfExist(questionEntity.getCreationDate()), questionEntity.getPhoto().getUrlPhoto(), questionEntity.getPhoto().getPictureName());

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

    public static QuizDto toQuizDto(QuizEntity quizEntity) {
        return new QuizDto(quizEntity.getId(), quizEntity.getQuizName(), quizEntity.getDescription());
    }
}
