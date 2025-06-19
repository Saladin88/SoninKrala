package co.simplon.soninkrala.services;


import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuizData;
import co.simplon.soninkrala.dtos.UserAnswerDto;

import java.util.List;

public interface QuizService {

    QuizData getAll();

    CorrectAnswer getCorrectAnswer(int id, UserAnswerDto userAnswer);

    List<UserAnswerDto> retrieveAllAnswers(int id);
}
