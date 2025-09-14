package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.dtos.WordDto;
import co.simplon.soninkrala.entities.WordEntity;
import co.simplon.soninkrala.jpaRepositories.WordJpaRepo;
import co.simplon.soninkrala.mappers.WordMapper;
import co.simplon.soninkrala.services.WordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class WordServiceImpl implements WordService {

    private final WordJpaRepo wordJpaRepo;

    public WordServiceImpl(WordJpaRepo wordJpaRepo) {
        this.wordJpaRepo = wordJpaRepo;
    }

    @Override
    public Set<WordDto> getAllWord() {
        List<WordEntity> wordEntities =  this.wordJpaRepo.findAll();
        return WordMapper.toWordSet(wordEntities);
    }
}
