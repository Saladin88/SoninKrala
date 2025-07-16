package co.simplon.soninkrala.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soninkrala/api/v1/pronunciations")
public class VoiceRecordController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendRecord() {

    }
}
