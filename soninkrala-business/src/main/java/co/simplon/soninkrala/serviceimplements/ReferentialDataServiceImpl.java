package co.simplon.soninkrala.serviceimplements;


import co.simplon.soninkrala.dtos.TermPolicyVersionDto;
import co.simplon.soninkrala.entities.TermVersionEntity;
import co.simplon.soninkrala.jpaRepositories.TermVersionJpaRepo;
import co.simplon.soninkrala.mappers.ReferentialDataMapper;
import co.simplon.soninkrala.services.ReferentialDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReferentialDataServiceImpl implements ReferentialDataService {

    private final TermVersionJpaRepo versionJpaRepo;

    @Value("${co.simplon.soninkrala.rgpd.version}")
    private String rgpdVersion;

    public ReferentialDataServiceImpl(TermVersionJpaRepo versionJpaRepo) {
        this.versionJpaRepo = versionJpaRepo;
    }

    @Override
    public TermPolicyVersionDto fetchTermVersion() {
        TermVersionEntity version = versionJpaRepo.findByVersion(rgpdVersion);
        return ReferentialDataMapper.toTermPolicyVersionDto(version);
    }
}
