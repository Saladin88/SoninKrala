package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import co.simplon.soninkrala.services.VoiceRecorderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VoiceRecorderServiceImpl implements VoiceRecorderService {

    @Value("${co.simplon.soninkrala.uri.python-api.pronunciation}")
    private String uriPythonApiPronunciation;

    private final WebClient webClient;

    public VoiceRecorderServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<PronunciationResultDto> sendRecordToPythonApi(AudioRecordDto audioRecordDto) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("audioFile", audioRecordDto.audioFile().getResource());
        builder.part("audioFileName", audioRecordDto.audioFileName());
        MultiValueMap<String, HttpEntity<?>> parts = builder.build();
        //HttpEntity<?> = contient les données + les headers de chaque partie
        return webClient.post()
                .uri(uriPythonApiPronunciation)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Erreur API Python : " + errorBody)))
                )
                .bodyToMono(PronunciationResultDto.class);

    }
}
