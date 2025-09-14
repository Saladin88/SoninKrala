package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.AccountPronunciationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPronunciationJpaRepo extends JpaRepository<AccountPronunciationEntity, Integer> {

}