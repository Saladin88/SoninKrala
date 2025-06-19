package co.simplon.soninkrala.dtos.validators;

import co.simplon.soninkrala.serviceimplements.QuizServiceImpl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistingQuestionIdValidator implements ConstraintValidator<ExistingQuestionId,Integer> {

    private final QuizServiceImpl quizService;

    public ExistingQuestionIdValidator(QuizServiceImpl quizService) {
        this.quizService = quizService;
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if(id == null) {
            return true;
        }
        return quizService.findQuestionId(id);
    }
}
