package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import co.simplon.soninkrala.services.VoiceRecorderService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/soninkrala/api/v1/pronunciations")
@Validated
public class VoiceRecordController {

    private final VoiceRecorderService voiceRecorderService;

    public VoiceRecordController(VoiceRecorderService voiceRecorderService) {
        this.voiceRecorderService = voiceRecorderService;
    }

    @PostMapping("/{word}/word")
    @ResponseStatus(HttpStatus.CREATED)
    public PronunciationResultDto sendRecord(
            @PathVariable @Size(min=1,max=50) @NotBlank String word,
            @RequestParam("audioFile") MultipartFile audioFile,
            Principal principal
    ) throws IOException {
        AudioRecordDto dto = new AudioRecordDto(audioFile, word);
        return voiceRecorderService.managePronunciationSendAndSave(principal, dto);
    }


}
