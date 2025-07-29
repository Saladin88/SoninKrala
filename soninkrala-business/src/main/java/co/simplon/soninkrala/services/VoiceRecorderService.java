package co.simplon.soninkrala.services;

import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import reactor.core.publisher.Mono;

public interface VoiceRecorderService {

    Mono<PronunciationResultDto> sendRecordToPythonApi(AudioRecordDto audioRecordDto);
}
