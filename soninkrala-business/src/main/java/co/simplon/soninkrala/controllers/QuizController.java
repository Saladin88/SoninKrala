package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuizData;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @ResponseStatus(code=HttpStatus.OK)
    public QuizData getQuestion() {
        return quizService.getAll();
    }
}
