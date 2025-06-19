package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.LetterWithAudio;
import co.simplon.soninkrala.services.LetterService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/soninkrala/api/v1/alphabet")
public class AlphabetController {

    private final LetterService letterService;

    public AlphabetController(LetterService letterService) {
        this.letterService = letterService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<LetterWithAudio> getAllLettersWithAudio() {
        return letterService.getAllLetterWithAudio();
    }
}
