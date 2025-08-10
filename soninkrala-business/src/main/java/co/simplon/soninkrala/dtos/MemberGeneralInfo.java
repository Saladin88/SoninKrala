package co.simplon.soninkrala.dtos;

import java.util.List;

public record MemberGeneralInfo(
        String username,
        String firstname,
        String lastname,
        String email,
        String role,
        String profilePictureUrl,
        List<QuizDto> quizList,
        List<PronunciationResultDto> pronunciationResults
        ) {
}
