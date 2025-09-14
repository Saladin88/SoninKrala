package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.WordDto;
import co.simplon.soninkrala.services.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(("/soninkrala/api/v1/words"))
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<WordDto> getAllWord() {
       return wordService.getAllWord();
    }

}
