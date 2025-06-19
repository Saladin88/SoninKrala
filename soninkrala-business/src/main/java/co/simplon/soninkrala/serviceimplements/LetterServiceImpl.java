package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.dtos.LetterWithAudio;
import co.simplon.soninkrala.entities.AudioLetterEntity;
import co.simplon.soninkrala.entities.LetterEntity;
import co.simplon.soninkrala.jpaRepositories.AudioLetterJpaRepo;
import co.simplon.soninkrala.jpaRepositories.LetterJpaRepo;
import co.simplon.soninkrala.mappers.AlphabetMapper;
import co.simplon.soninkrala.services.LetterService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService {

    private final LetterJpaRepo letterJpaRepo;
    private final AudioLetterJpaRepo audioLetterJpaRepo;

    public LetterServiceImpl(LetterJpaRepo letterJpaRepo, AudioLetterJpaRepo audioLetterJpaRepo) {
        this.letterJpaRepo = letterJpaRepo;
        this.audioLetterJpaRepo = audioLetterJpaRepo;
    }

    @Override
    public List<LetterWithAudio> getAllLetterWithAudio() {
        List<LetterEntity> letterEntities = letterJpaRepo.findAllByOrderByLetterOrderAsc();
        return AlphabetMapper.toLetterWithAudioList(letterEntities);
    }
}
