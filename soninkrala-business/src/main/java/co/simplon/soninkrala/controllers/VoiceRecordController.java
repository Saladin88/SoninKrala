package co.simplon.soninkrala.controllers;

import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import co.simplon.soninkrala.services.VoiceRecorderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/soninkrala/api/v1/pronunciations")
public class VoiceRecordController {

    private final VoiceRecorderService voiceRecorderService;

    public VoiceRecordController(VoiceRecorderService voiceRecorderService) {
        this.voiceRecorderService = voiceRecorderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PronunciationResultDto> sendRecord(
            @RequestParam("audioFile") MultipartFile audioFile,
            @RequestParam("audioFileName") String audioFileName
    ) throws IOException {
        AudioRecordDto dto = new AudioRecordDto(audioFile, audioFileName);
        return voiceRecorderService.sendRecordToPythonApi(dto);
    }
}
