package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.LetterWithAudio;
import co.simplon.soninkrala.entities.LetterEntity;

import java.util.List;

public class AlphabetMapper {

    private AlphabetMapper() {
        //Static methods
    }

    public static List<LetterWithAudio> toLetterWithAudioList(List<LetterEntity> letterEntities) {
        return letterEntities.stream().map(AlphabetMapper::toLetterWithAudioDto).toList();
    }
    public static LetterWithAudio toLetterWithAudioDto(LetterEntity letterEntity) {
         return new LetterWithAudio(letterEntity.getLetterName(), letterEntity.getAudioLetter().getUrlLink(), letterEntity.getPhotoEntity().getPictureName(),letterEntity.getPhotoEntity().getUrlPhoto());
    }
}
