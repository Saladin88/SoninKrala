package co.simplon.soninkrala.dtos;

import java.time.OffsetDateTime;

public record TermPolicyVersionDto(
        int idVersion,
        String version,
        OffsetDateTime publishedAt,
        String labelVersion
) {
}
