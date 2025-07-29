package co.simplon.soninkrala.services;


import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuestionQuiz;
import co.simplon.soninkrala.dtos.QuizDto;
import co.simplon.soninkrala.dtos.UserAnswerDto;

import java.util.List;
import java.util.Set;

public interface QuizService {

    Set<QuestionQuiz> getAllQuestionsForQuizId(int id);

    CorrectAnswer getCorrectAnswer(int id, UserAnswerDto userAnswer);

    List<UserAnswerDto> retrieveAllAnswers(int id);

    Set<QuizDto> getAllQuiz();
}
