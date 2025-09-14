package co.simplon.soninkrala.services;

import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import reactor.core.publisher.Mono;

import java.security.Principal;

public interface VoiceRecorderService {

    PronunciationResultDto managePronunciationSendAndSave(Principal principal, AudioRecordDto dto);
}
