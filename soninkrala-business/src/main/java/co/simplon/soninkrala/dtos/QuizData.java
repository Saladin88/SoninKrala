package co.simplon.soninkrala.dtos;


import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record QuizData(
        @NotEmpty
        List<QuestionQuiz> data
) {

}
