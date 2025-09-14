package co.simplon.soninkrala.mappers;

import co.simplon.soninkrala.dtos.TermPolicyVersionDto;
import co.simplon.soninkrala.entities.TermVersionEntity;

public class ReferentialDataMapper {

    private ReferentialDataMapper() {
        //static methods
    }

    public static TermPolicyVersionDto toTermPolicyVersionDto(TermVersionEntity version) {
        return new TermPolicyVersionDto(version.getId(), version.getVersion(), version.getPublishedAt(), version.getLabelVersion());
    }
}
