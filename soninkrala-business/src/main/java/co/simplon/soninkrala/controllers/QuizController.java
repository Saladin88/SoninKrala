package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuestionQuiz;
import co.simplon.soninkrala.dtos.QuizDto;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/soninkrala/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    };

    @GetMapping("/{id}/answers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAnswerDto> getAnswers(@PathVariable int id) {
        return quizService.retrieveAllAnswers(id);

    }

    @PostMapping("/{id}/correct-answer")
    @ResponseStatus(HttpStatus.CREATED)
    public CorrectAnswer getCorrectAnswer(@PathVariable int id, @Valid @RequestBody UserAnswerDto userAnswer) {
        return quizService.getCorrectAnswer(id,userAnswer);
    }

    @GetMapping("/{id}/questions")
    @ResponseStatus(code=HttpStatus.OK)
    public Set<QuestionQuiz> getQuestionByQuizId(@PathVariable int id) {
        return quizService.getAllQuestionsForQuizId(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Set<QuizDto> getAllQuiz() {
        return this.quizService.getAllQuiz();
    }
}
