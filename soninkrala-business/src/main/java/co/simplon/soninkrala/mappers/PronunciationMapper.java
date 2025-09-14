package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.PronunciationResultDto;
import co.simplon.soninkrala.entities.AccountEntity;
import co.simplon.soninkrala.entities.AccountPronunciationEntity;
import co.simplon.soninkrala.entities.WordEntity;

public class PronunciationMapper {
    private PronunciationMapper() {
        //static methods
    }

    public static AccountPronunciationEntity toPronunciationEntity(PronunciationResultDto result, AccountEntity account, WordEntity word) {
        AccountPronunciationEntity accountPronunciation = new AccountPronunciationEntity();
        accountPronunciation.setSimilarityScore(result.score());
        accountPronunciation.setWord(word);
        accountPronunciation.setAccount(account);
        return accountPronunciation;
    }

    public static PronunciationResultDto toPronunciationResultDto(AccountPronunciationEntity pronunciationSaved) {
        return new PronunciationResultDto(pronunciationSaved.getWord().getWordLabel(),pronunciationSaved.getSimilarityScore());
    }
}