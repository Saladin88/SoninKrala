package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.WordDto;
import co.simplon.soninkrala.entities.WordEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordMapper {
    private WordMapper() {
        //static methods
    }

    public static Set<WordDto> toWordSet(List<WordEntity> wordEntities) {
        return wordEntities.stream().map(WordMapper::toWord).collect(Collectors.toSet());
    }

    private static WordDto toWord(WordEntity wordEntity) {
        return new WordDto(wordEntity.getId(), wordEntity.getWordLabel(), wordEntity.getLanguageCode());
    }
}
