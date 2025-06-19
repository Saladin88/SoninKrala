package co.simplon.soninkrala.dtos;

import org.springframework.lang.Nullable;

public record LetterWithAudio(
        String letter,
        String urlAudio,
        String photoName,
        String urlPhoto
) {
}
