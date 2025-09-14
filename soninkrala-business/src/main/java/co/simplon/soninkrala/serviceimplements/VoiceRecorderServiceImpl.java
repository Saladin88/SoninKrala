package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.controllers.errors.AccountErrorMessage;
import co.simplon.soninkrala.controllers.errors.ReferentialDataError;
import co.simplon.soninkrala.dtos.AudioRecordDto;
import co.simplon.soninkrala.dtos.PronunciationResultDto;
import co.simplon.soninkrala.entities.AccountEntity;
import co.simplon.soninkrala.entities.AccountPronunciationEntity;
import co.simplon.soninkrala.entities.WordEntity;
import co.simplon.soninkrala.jpaRepositories.AccountJpaRepo;
import co.simplon.soninkrala.jpaRepositories.AccountPronunciationJpaRepo;
import co.simplon.soninkrala.jpaRepositories.WordJpaRepo;
import co.simplon.soninkrala.mappers.PronunciationMapper;
import co.simplon.soninkrala.services.VoiceRecorderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static software.amazon.awssdk.http.auth.aws.internal.signer.V4RequestSigner.header;

@Service
@Transactional(readOnly = true)
public class VoiceRecorderServiceImpl implements VoiceRecorderService {

    private final AccountPronunciationJpaRepo accountPronunciationJpaRepo;
    @Value("${co.simplon.soninkrala.uri.python-api.pronunciation}")
    private String uriPythonApiPronunciation;

    @Value("${co.simplon.soninkrala.secret_shared_key_api}")
    private String flaskSharedSecret;

    private final WebClient webClient;
    private final AccountJpaRepo accountJpaRepo;
    private final WordJpaRepo wordJpaRepo;
    private final AccountPronunciationJpaRepo pronunciationJpaRepo;

    public VoiceRecorderServiceImpl(WebClient webClient, AccountJpaRepo accountJpaRepo, WordJpaRepo wordJpaRepo, AccountPronunciationJpaRepo pronunciationJpaRepo, AccountPronunciationJpaRepo accountPronunciationJpaRepo) {
        this.webClient = webClient;
        this.accountJpaRepo = accountJpaRepo;
        this.wordJpaRepo = wordJpaRepo;
        this.pronunciationJpaRepo = pronunciationJpaRepo;
        this.accountPronunciationJpaRepo = accountPronunciationJpaRepo;
    }

    private PronunciationResultDto sendRecordToPythonApi(AudioRecordDto audioRecordDto) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("audioFile", audioRecordDto.audioFile().getResource());
        builder.part("audioFileName", audioRecordDto.audioFileName());
        MultiValueMap<String, HttpEntity<?>> parts = builder.build();
        //HttpEntity<?> = contient les données + les headers de chaque partie
        return webClient.post()
                .uri(uriPythonApiPronunciation)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Api-Key", flaskSharedSecret)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Erreur API Python : " + errorBody)))
                )
                .bodyToMono(PronunciationResultDto.class)
                .block();
    }

    @Override
    @Transactional
    public PronunciationResultDto managePronunciationSendAndSave(Principal principal, AudioRecordDto audioRecordDto) {
        AccountEntity accountEntity = accountJpaRepo.findByUsernameIgnoreCase(principal.getName()).orElseThrow(() -> new AccountErrorMessage("Account not found/not existing with username : " + principal.getName()));
        WordEntity wordEntity = wordJpaRepo.findByWordLabel(audioRecordDto.audioFileName()).orElseThrow(()-> new ReferentialDataError("Word not found = " + audioRecordDto.audioFileName()));
        AudioRecordDto recordValid = new AudioRecordDto(audioRecordDto.audioFile(), wordEntity.getWordLabel());
        PronunciationResultDto pronunciationResult = sendRecordToPythonApi(recordValid);
        AccountPronunciationEntity pronunciationSaved = savePronunciationAttempt(pronunciationResult,accountEntity,wordEntity);
        return PronunciationMapper.toPronunciationResultDto(pronunciationSaved);
    }

    @Transactional
    protected AccountPronunciationEntity savePronunciationAttempt(PronunciationResultDto result, AccountEntity account,WordEntity word) {
        AccountPronunciationEntity pronunciationAttempt =  PronunciationMapper.toPronunciationEntity(result,account,word);
        return accountPronunciationJpaRepo.save(pronunciationAttempt);
    }
}
